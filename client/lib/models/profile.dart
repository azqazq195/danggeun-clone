class Profile {
  final String id;
  final String email;
  final String nickname;
  final String phone;
  final String? profileUrl;
  final double temperature;
  final List<String> regions;

  Profile(this.id, this.phone, this.email, this.nickname, this.profileUrl,
      this.temperature, this.regions);

  Profile.fromJson(Map<String, dynamic> json)
      : id = json['id'].toString(),
        phone = json['phone'],
        email = json['email'],
        nickname = json['nickname'],
        profileUrl = json['profileUrl'],
        temperature = json['temperature'].toDouble(),
        regions = json['regions'].cast<String>();

  Map<String, dynamic> toJson() => {
        'id': id,
        'phone': phone,
        'email': email,
        'nickname': nickname,
        'profileUrl': profileUrl,
        'temperature': temperature,
        'regions': regions,
      };
}
