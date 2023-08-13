import 'dart:convert';

import 'package:app/models/item_favorites.dart';
import 'package:app/models/profile.dart';
import 'package:app/models/token.dart';
import 'package:http/http.dart' as http;

class AccountService {
  // static const String _baseURL = 'arong.info:7004';
  final String _baseURL = 'localhost:8080';

  // 로그인
  Future<Token> signIn(http.Client client) async {
    // http://arong.info:7004/user?phoneNum=01011112222
    // var url = Uri.http(_baseURL, '/user', {'phoneNum': phoneNum});
    var headers = {
      "Content-Type": "application/json",
    };
    var body = {'email': 'moseoh@gmail.com', 'password': 'password'};
    var url = Uri.http(_baseURL, '/auth/sign-in');

    final response =
        await client.post(url, headers: headers, body: jsonEncode(body));
    if (response.statusCode == 200) {
      final tokenMap = jsonDecode(utf8.decode(response.bodyBytes));
      if (tokenMap.containsKey("content")) {
        var token = Token.fromJson(tokenMap["content"]);
        return token;
      } else {
        throw Exception('No account data');
      }
    } else {
      throw Exception('Unable to fetch account from the REST API');
    }
  }

  // 프로필 정보 요청
  Future<Profile> fetchProfile(http.Client client) async {
    var token = await signIn(client);

    var headers = {
      'Authorization': token.accessToken,
      "Content-Type": "application/json; charset=utf-8",
    };

    var url = Uri.http(_baseURL, '/auth/me');

    final response = await client.get(url, headers: headers);
    if (response.statusCode == 200) {
      final profileMap = jsonDecode(utf8.decode(response.bodyBytes));
      if (profileMap.containsKey("content")) {
        var profile = Profile.fromJson(profileMap["content"]);
        return profile;
      } else {
        throw Exception('No account data');
      }
    } else {
      throw Exception('Unable to fetch account from the REST API');
    }
  }

  // 등록한 관심상품 정보 요청
  Future<ItemFavorites?> fetchItemFavorites(
      http.Client client, String userId) async {
    // http://arong.info:7004/ItemFavorites?userId=-NIVCK7LmyT6E1bumyAQ
    var url = Uri.http(_baseURL, '/ItemFavorites', {'userId': userId});

    final response = await client.get(url);
    if (response.statusCode == 200) {
      final itemFavoritesMap = jsonDecode(utf8.decode(response.bodyBytes));
      if (itemFavoritesMap.containsKey("rows")) {
        if (itemFavoritesMap["rows"].length <= 0) return null;
        var itemFavorites = ItemFavorites.fromJson(itemFavoritesMap["rows"][0]);
        return itemFavorites;
      } else {
        throw Exception('No item favorites data');
      }
    } else {
      throw Exception('Unable to fetch itemFavorites from the REST API');
    }
  }

  // 등록한 관심상품 정보 업데이트
  Future<bool> updateItemFavorites(
      http.Client client, ItemFavorites itemFavorites) async {
    Uri url;
    if (itemFavorites.id != "") {
      print('관심상품 정보 업데이트');
      // http://arong.info:7004/SetItemFavorites/{ItemFavorites ID}
      url = Uri.http(_baseURL, '/SetItemFavorites/${itemFavorites.id}');
    } else {
      print('관심상품 최초 등록');
      // http://arong.info:7004/AddItemFavorites
      url = Uri.http(_baseURL, '/AddItemFavorites');
    }
    final body = json.encode(itemFavorites.toJson());
    final response = await client.post(url,
        headers: {"Content-Type": "application/json"}, body: body);

    if (response.statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }
}
