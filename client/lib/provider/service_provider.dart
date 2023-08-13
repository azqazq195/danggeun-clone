import 'package:app/models/articles.dart';
import 'package:app/models/item_favorites.dart';
import 'package:app/models/profile.dart';
import 'package:app/services/accountService.dart';
import 'package:app/services/articlesService.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';

class ServiceProvider extends ChangeNotifier {
  // 계정 데이터 처리 서비스
  final AccountService _accountService = AccountService();

  // 게시글 데이터 처리 서비스
  final ArticlesService _articlesService = ArticlesService();

  // 계정 데이터
  Profile? _profile;

  // 현재 선택된 동네
  String? _currentTown;

  // 게시글 데이터
  List<Articles>? _articles;

  // 판매자 게시글 데이터
  List<Articles>? _sellerArticles;

  // 판매 상품 상세 정보
  Articles? _detailArticle;

  // 등록된 관심상품 정보
  ItemFavorites? _itemFavorites;

  // 데이터 요청중
  bool _isDataFetching = false;

  // get, set 프로퍼티
  Profile? get profile => _profile;

  List<Articles>? get articles => _articles;

  List<Articles>? get sellerArticles => _sellerArticles;

  Articles? get detailArticle => _detailArticle;

  String? get currentTown => _currentTown;

  set currentTown(String? town) {
    _currentTown = town;
    notifyListeners();
  }

  ItemFavorites? get itemFavorites => _itemFavorites;

  bool get isDataFetching => _isDataFetching;

  // get, set 프로퍼티 END

  // 계정 데이터 요청
  fetchProfile() async {
    var profile = await _accountService.fetchProfile(http.Client());
    _profile = profile;
    if (_profile != null && _profile!.regions.isNotEmpty) {
      // 등록된 관심상품 데이터 요청, 관심상품이 없다면 _itemFavorites is null
      await fetchItemFavorites();

      // 현재 선택된 동네
      _currentTown = _profile!.regions[0];
      // 선택된 동네의 판매 상품 정보 데이터 요청
      await fetchArticles(_currentTown!);
      notifyListeners();
    }
  }

  // 등록된 관심상품 정보 데이터 요청
  fetchItemFavorites() async {
    var itemFavorites =
        await _accountService.fetchItemFavorites(http.Client(), _profile!.id);
    _itemFavorites = itemFavorites;
  }

  // 관심상품 update
  Future<bool> updateItemFavorites(ItemFavorites itemFavorites) async {
    var result =
        await _accountService.updateItemFavorites(http.Client(), itemFavorites);
    return result;
  }

  // 데이터 요청중
  dataFetching({bool isNotify = true}) {
    _isDataFetching = true;
    if (isNotify) notifyListeners();
  }

  // 게시글 데이터 요청
  fetchArticles(String town) async {
    print('데이터 요청 - ${town}');

    // 딜레이 테스트
    //await Future.delayed(const Duration(seconds: 7));

    var articles = await _articlesService.fetchArticles(http.Client(), town);
    _isDataFetching = false;
    _articles = articles;

    notifyListeners();
  }

  // 게시글 상세 데이터 요청
  fetchDetailArticle(String articleId) async {
    print('게시 조회수 Update(read cnt) - ${articleId}');
    await _articlesService.updateArticleReadCnt(http.Client(), articleId);

    print('상세 데이터 요청 - ${articleId}');
    var detailArticle =
        await _articlesService.fetchDetailArticle(http.Client(), articleId);
    _isDataFetching = false;
    _detailArticle = detailArticle;

    notifyListeners();
  }

  // 판매자 게시글 데이터 조회
  fetchArticlesByProfile(Profile profile) async {
    print('판매자 게시글 데이터 요청 - ${profile.phone}');

    var sellerArticles =
        await _articlesService.fetchArticlesByProfile(http.Client(), profile);
    _isDataFetching = false;
    _sellerArticles = sellerArticles;

    notifyListeners();
  }

  // 등록한 관심상품 데이터 조회
  fetchFavoritesArticles() async {
    if (_profile == null) {
      print('계정 정보를 정상적으로 불러오지 못했습니다.');
      return;
    }

    print('등록한 관심상품 데이터 요청 - ${_profile!.phone}');

    var favoritesArticles =
        await _articlesService.fetchFavoritesArticles(http.Client(), _profile!);
    _isDataFetching = false;
    _articles = favoritesArticles;

    notifyListeners();
  }

  // 판매중인 데이터 조회
  fetchSalesHistoryArticles() async {
    if (_profile == null) {
      print('계정 정보를 정상적으로 불러오지 못했습니다.');
      return;
    }

    print('판매중인 데이터 요청 - ${_profile!.phone}');

    var salesHistoryArticles =
        await _articlesService.fetchArticlesByProfile(http.Client(), _profile!);
    _isDataFetching = false;
    _articles = salesHistoryArticles;

    notifyListeners();
  }

  // 중고물품 데이터 등록 요청
  Future<bool> addArticle(
      List<MultipartFile> uplopadImages, Articles article) async {
    print('중고물품 데이터 등록 요청 - ${_profile!.phone}');

    var result = await _articlesService.addArticle(
        http.Client(), uplopadImages, article);
    _isDataFetching = false;
    notifyListeners();
    return result;
  }

  // 중고거래 상품 제거
  Future<bool> removeArticle(Articles article) async {
    print('중고물품 데이터 제거 요청 - ${article.id}');

    var result = await _articlesService.removeArticle(http.Client(), article);
    _isDataFetching = false;
    notifyListeners();
    return result;
  }

  dispose() {
    if (_articles != null) _articles!.clear();
  }
}