package com.example.travel;

import com.example.travel.authentication.user.entity.Authority;
import com.example.travel.authentication.user.entity.UserEntity;
import com.example.travel.authentication.user.repo.AuthorityRepo;
import com.example.travel.authentication.user.repo.UserRepo;
import com.example.travel.bookTours.entity.BookTours;
import com.example.travel.bookTours.repo.BookToursRepo;
import com.example.travel.post.comment.entity.Comment;
import com.example.travel.post.comment.repo.CommentRepo;
import com.example.travel.post.emotion.Like;
import com.example.travel.post.emotion.LikeRepo;
import com.example.travel.post.posting.entity.ImagePosting;
import com.example.travel.post.posting.entity.Posting;
import com.example.travel.post.posting.repo.ImagePostRepo;
import com.example.travel.post.posting.repo.PostRepo;
import com.example.travel.tourPackage.entity.Package;
import com.example.travel.tourPackage.entity.Schedule;
import com.example.travel.tourPackage.repo.PackageRepo;
import com.example.travel.tourPackage.repo.ScheduleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class TestData {
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthorityRepo authorityRepo;
    private final PostRepo postRepo;
    private final ImagePostRepo imageRepo;
    private final PackageRepo packageRepo;
    private final BookToursRepo bookToursRepo;
    private final ScheduleRepo scheduleRepo;
    private final LikeRepo likeRepo;
    private final CommentRepo commentRepo;

    public TestData(
            UserRepo userRepo, PasswordEncoder encoder,
            AuthorityRepo authorityRepo, PostRepo postRepo,
            ImagePostRepo imagePostRepo, PackageRepo packageRepo,
            BookToursRepo bookToursRepo, ScheduleRepo scheduleRepo, LikeRepo likeRepo, CommentRepo commentRepo
    ) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.authorityRepo = authorityRepo;
        this.postRepo = postRepo;
        this.imageRepo = imagePostRepo;
        this.packageRepo = packageRepo;
        this.bookToursRepo = bookToursRepo;
        this.scheduleRepo = scheduleRepo;
        this.likeRepo = likeRepo;
        this.commentRepo = commentRepo;
        this.fixUser();
        this.fixPost();
        this.fixComment();
        this.package1();
        this.package2();
        this.package3();
        this.fixBooking();
    }

    public void fixUser() {
        Authority authorityAdmin = authorityRepo.findByRole("ROLE_ADMIN").orElseThrow();
        Authority authorityUser = authorityRepo.findByRole("ROLE_USER").orElseThrow();

        UserEntity admin = UserEntity.builder()
                .username("luna010209")
                .password(encoder.encode("1234"))
                .name("서울 여행")
                .email("luna@gmail.com")
                .phone("010-1234-5654")
                .profileImg("/static/1/profile.png")
                .build();
        admin.getAuthorities().add(authorityAdmin);
        admin.getAuthorities().add(authorityUser);
        userRepo.save(admin);

        UserEntity user2 = UserEntity.builder()
                .username("user2")
                .password(encoder.encode("1234"))
                .name("렁찬")
                .email("user2@gmail.com")
                .phone("010-1256-7098")
                .profileImg("/static/2/profile.png")
                .build();
        user2.getAuthorities().add(authorityUser);
        userRepo.save(user2);

        UserEntity user3 = UserEntity.builder()
                .username("user3")
                .password(encoder.encode("1234"))
                .name("시안")
                .email("user3@gmail.com")
                .phone("010-1234-4321")
                .profileImg("/static/3/profile.png")
                .build();

        user3.getAuthorities().add(authorityUser);
        userRepo.save(user3);

        UserEntity user4 = UserEntity.builder()
                .username("user4")
                .password(encoder.encode("1234"))
                .name("채연")
                .email("user4@gmail.com")
                .phone("010-4563-0543")
                .profileImg("/static/4/profile.png")
                .build();
        user4.getAuthorities().add(authorityUser);
        userRepo.save(user4);

        UserEntity user5 = UserEntity.builder()
                .username("user5")
                .password(encoder.encode("1234"))
                .name("서우")
                .email("user5@gmail.com")
                .phone("010-4563-0543")
                .profileImg("/static/5/profile.png")
                .build();
        user5.getAuthorities().add(authorityUser);
        userRepo.save(user5);

        UserEntity user6 = UserEntity.builder()
                .username("user6")
                .password(encoder.encode("1234"))
                .name("Alex")
                .email("user6@gmail.com")
                .phone("010-1235-0987")
                .profileImg("/static/6/profile.png")
                .build();
        user6.getAuthorities().add(authorityUser);
        userRepo.save(user6);

        UserEntity user7 = UserEntity.builder()
                .username("user7")
                .password(encoder.encode("1234"))
                .name("Jordan")
                .email("user7@gmail.com")
                .phone("010-3456-6754")
                .profileImg("/static/visual/user.png")
                .build();
        user7.getAuthorities().add(authorityUser);
        userRepo.save(user7);

        UserEntity user8 = UserEntity.builder()
                .username("user8")
                .password(encoder.encode("1234"))
                .name("Taylor")
                .email("user8@gmail.com")
                .phone("010-2345-1234")
                .profileImg("/static/visual/user.png")
                .build();
        user8.getAuthorities().add(authorityUser);
        userRepo.save(user8);

        UserEntity user9 = UserEntity.builder()
                .username("user9")
                .password(encoder.encode("1234"))
                .name("Morgan")//tour api
                .email("user9@gmail.com")
                .phone("010-3452-2345")
                .profileImg("/static/visual/user.png")
                .build();
        user9.getAuthorities().add(authorityUser);
        userRepo.save(user9);

        UserEntity user10 = UserEntity.builder()
                .username("user10")
                .password(encoder.encode("1234"))
                .name("Riley")
                .email("user10@gmail.com")
                .phone("010-2246-2432")
                .profileImg("/static/visual/user.png")
                .build();
        user10.getAuthorities().add(authorityUser);
        userRepo.save(user10);
    }
    public void fixPost(){
        UserEntity user2 = userRepo.findById(2L).orElseThrow();
        UserEntity user3 = userRepo.findById(3L).orElseThrow();
        UserEntity user4 = userRepo.findById(4L).orElseThrow();
        UserEntity user5 = userRepo.findById(5L).orElseThrow();
        UserEntity user6 = userRepo.findById(6L).orElseThrow();
        Posting posting21= Posting.builder()
                .title("시티투어 버스 타고, 비 오는 서울 여행")
                .content("시티투어 버스 타고, 비 오는 서울 여행 2024.05.05 ~ 2024.05.06 아기 시골쥐의, 서울 여행 1일차 : 광화문광장  " +
                        "- 시티투어버스 매표소 - N서울남산타워 - 청와대 차이들 안녕인사동점 - 조계사 우리 집 꼬마" +
                        "                    \n" +
                        "                    \n" +
                        "국내 유일 해변에 위치한 대관람차로 설악산의 절경과 속초의 푸른 바다를 한눈에 감상할 수 있어 8월 가볼만한 국내여행지로 추천합니다. 속초 해수욕장에서 차량으로 5분 거리에는 " +
                        "속초관광수산시장이 있어 먹을 것을 포장해오기에도 좋습니다." +
                        "                    \n" +
                        "                    \n" +
                        "시장에서 다시 차량으로 5분 가량에 위치한 영금정은 일출과 일몰 명소로도 유명한데요. 8월의 햇볕을 피해 이른 아침이나 선선한 저녁에 방문하는 것이 좋겠죠. 속초에 위치한 설악 " +
                        "워터피아는 낮에는 워터피아의 어트랙션을, 밤에는 여러 컨셉의 온천욕을 즐기기에도 좋아 당일치기 여행에도 적합합니다.\n" +
                        "[출처] 8월 가볼만한 국내 여행지 추천 BEST6 (+코스, 2023ver)|작성자 여행톡톡")
                .writer(user2)
                .build();
        postRepo.save(posting21);

        Posting posting22 = Posting.builder()
                .title("서울에서 꼭 가봐야 할 명소 10곳")
                .content("맑은 바닷물로 최근 '스노클링'의 성지로 떠오른 삼척입니다. 바닷물이 아직은 차가운 7월 초보다 8월에 방문해 스노클링을 즐기는 것이 좋은데요. 맑은 바다에서 물고기와 산호초를 볼\n" +
                        "                    수 있음은 물론 천혜의 자연환경이 펼쳐지는 삼척으로 8월 국내여행을 떠나보는 것은 어떠신가요.\n" +
                        "                    [출처] 8월 가볼만한 국내 여행지 추천 BEST6 (+코스, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    무건리 이끼폭포는 가는 길이 험해 거동이 불편한 분들은 방문이 어려우나 왕복 약 3시간이 의 험한 길을 지나 마주한 이끼폭포는 한 폭의 그림을 연상케합니다. 미인폭포는 길 정비가\n" +
                        "                    잘 되어있어 거동이 불편하신 분들이라도 등산스틱을 지참하면 무리없는데요. 옥색의 물빛을 자랑하고 있어 이국적인 분위기까지 내뿜습니다.\n" +
                        "                    [출처] 8월 가볼만한 국내 여행지 추천 BEST6 (+코스, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    모노레일을 타고 들어가는 '환선굴'은 석회암 동굴로 국내 최대 규모의 동굴인데요. 여름에도 얇은 겉옷을 걸쳐입어야하는 시원한 동굴에서 여러 동굴 동물과 동굴류를 탐사해볼 수\n" +
                        "                    있습니다. 어린아이들과 부모님과 함께 가기에도 좋아 8월 가볼만한 삼척 여행지로 추천합니다.\n" +
                        "                    [출처] 8월 가볼만한 국내 여행지 추천 BEST6 (+코스, 2023ver)|작성자 여행톡톡")
                .writer(user5)
                .build();
        postRepo.save(posting22);

        Posting posting31 = Posting.builder()
                .title("서울의 숨겨진 보석 같은 장소들")
                .content("서울에는 잘 알려지지 않은 숨겨진 명소들이 많습니다. "+
                        "북촌 한옥마을에서 전통 한옥의 아름다움을 감상하고, 익선동의 감성 카페들을 방문해 보세요. "+
                        "성수동의 힙한 카페와 갤러리도 놓칠 수 없습니다. "+
                        "이화동 벽화마을은 산책하며 예쁜 벽화를 구경하기 좋은 곳입니다. "+
                        "마지막으로, 서울숲에서 자연을 만끽하며 여유로운 시간을 보내세요. "+
                        "다양한 식물과 동물들이 있는 서울숲은 도시의 번잡함을 잠시 잊게 해줄 것입니다.")
                .writer(user3)
                .build();
        postRepo.save(posting31);

        Posting posting32 = Posting.builder()
                .title("서울에서 즐기는 최고의 맛집 투어")
                .content("서울은 맛집 천국입니다. 광장시장에서 다양한 전통 음식을 맛보고, 이태원에서는 세계 각국의 요리를 즐길 수 있습니다. " +
                        "가로수길과 연남동의 트렌디한 카페와 레스토랑도 추천합니다. 남대문시장의 칼국수 골목과 맛있는 길거리 음식도 놓치지 마세요. " +
                        "마포구의 유명한 불고기 맛집도 꼭 방문해 보세요. 또한, 한남동의 고급 레스토랑에서 특별한 한 끼를 즐기며 서울의 미식 문화를 체험할 수 있습니다.\n" +
                        "\n")
                .writer(user2)
                .build();
        postRepo.save(posting32);

        Posting posting41 = Posting.builder()
                .title("서울의 숨겨진 보석 같은 장소들")
                .content("서울은 역사와 현대가 어우러진 매력적인 도시입니다. 여행자들에게 추천하는 서울의 명소 다섯 곳을 소개합니다.\n" +
                        "                    \n" +
                        "                    첫 번째로 추천하는 곳은 경복궁입니다. 조선시대의 왕궁으로, 고궁의 아름다움과 더불어 한국 전통문화 체험을 즐길 수 있습니다. 10월에는 단풍이 물들어 더욱 아름답습니다.\n" +
                        "                    [출처] 서울에서 꼭 가봐야 할 여행지 BEST5 (+코스, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    두 번째로는 북촌 한옥마을입니다. 전통 한옥이 모여 있는 이곳에서는 과거와 현재가 공존하는 특별한 분위기를 느낄 수 있습니다. 골목길을 걸으며 카페와 갤러리를 방문해보세요.\n" +
                        "                    [출처] 서울에서 꼭 가봐야 할 여행지 BEST5 (+코스, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    세 번째로는 남산타워입니다. 서울의 전경을 한눈에 볼 수 있는 전망대입니다. 저녁에는 서울의 야경을 감상하며 로맨틱한 시간을 보낼 수 있습니다.\n" +
                        "                    [출처] 서울에서 꼭 가봐야 할 여행지 BEST5 (+코스, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    네 번째로는 홍대입니다. 젊음과 열정이 넘치는 거리로, 다양한 먹거리와 볼거리를 즐길 수 있습니다. 특히 밤이 되면 다양한 공연과 축제가 열려 더욱 활기찬 분위기를 자아냅니다.\n" +
                        "                    [출처] 서울에서 꼭 가봐야 할 여행지 BEST5 (+코스, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    마지막으로 추천하는 곳은 청계천입니다. 도심 속의 쉼터로, 산책을 하며 여유를 즐길 수 있는 곳입니다. 야경이 아름다워 사진 찍기에도 좋은 장소입니다.\n" +
                        "                    [출처] 서울에서 꼭 가봐야 할 여행지 BEST5 (+코스, 2023ver)|작성자 여행톡톡")
                .writer(user6)
                .build();
        postRepo.save(posting41);

        Posting posting42 = Posting.builder()
                .title("여행 가방 준비 필수 아이템과 팁 (+체크리스트, 2023ver)")
                .content("여행을 떠나기 전, 여행 가방을 잘 준비하는 것은 매우 중요합니다. 여기에는 필수 아이템과 팁을 소개합니다.\n" +
                        "                    \n" +
                        "                    첫 번째로 준비해야 할 것은 여행 서류입니다. 여권, 비자, 항공권, 예약 확인서 등 중요한 서류를 빠뜨리지 말고 챙기세요. 이를 한 곳에 모아두면 분실 위험을 줄일 수 있습니다.\n" +
                        "                    [출처] 여행 가방 준비 필수 아이템과 팁 (+체크리스트, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    두 번째로는 의류와 신발입니다. 여행 기간과 목적지의 날씨를 고려하여 적절한 옷을 준비하세요. 기본적인 옷 외에도 방수 재킷, 편안한 신발, 수영복 등을 준비하면 좋습니다.\n" +
                        "                    [출처] 여행 가방 준비 필수 아이템과 팁 (+체크리스트, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    세 번째로는 개인 위생 용품입니다. 칫솔, 치약, 세면도구, 개인 약품 등을 챙기세요. 여행 중에는 휴대용 손 세정제와 마스크도 필요할 수 있습니다.\n" +
                        "                    [출처] 여행 가방 준비 필수 아이템과 팁 (+체크리스트, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    네 번째로는 전자 기기입니다. 스마트폰, 충전기, 보조 배터리, 카메라 등을 챙기세요. 여행지에서 사용하기 편리한 멀티 어댑터도 준비하면 좋습니다.\n" +
                        "                    [출처] 여행 가방 준비 필수 아이템과 팁 (+체크리스트, 2023ver)|작성자 여행톡톡" +
                        "                    \n" +
                        "                    \n" +
                        "                    마지막으로, 여행 가방 자체도 신경 써야 합니다. 튼튼하고 가벼운 가방을 선택하세요. 짐을 효율적으로 정리할 수 있는 파우치와 지퍼백을 활용하면 더욱 편리합니다.\n" +
                        "                    [출처] 여행 가방 준비 필수 아이템과 팁 (+체크리스트, 2023ver)|작성자 여행톡톡")
                .writer(user4)
                .build();
        postRepo.save(posting42);
    }
    public void fixComment(){
        UserEntity user5 = userRepo.findById(5L).orElseThrow();
        UserEntity user6 = userRepo.findById(6L).orElseThrow();
        UserEntity user7 = userRepo.findById(7L).orElseThrow();
        UserEntity user8 = userRepo.findById(8L).orElseThrow();
        UserEntity user9 = userRepo.findById(9L).orElseThrow();
        UserEntity user10 = userRepo.findById(10L).orElseThrow();

        Posting post1 = postRepo.findById(1L).orElseThrow();
        Posting post2 = postRepo.findById(2L).orElseThrow();
        Posting post3 = postRepo.findById(3L).orElseThrow();
        likeRepo.save(Like.builder().like(true).posting(post1).user(user10).build());
        likeRepo.save(Like.builder().like(true).posting(post1).user(user9).build());
        likeRepo.save(Like.builder().like(true).posting(post1).user(user8).build());
        likeRepo.save(Like.builder().like(true).posting(post1).user(user7).build());
        likeRepo.save(Like.builder().like(true).posting(post1).user(user6).build());
        likeRepo.save(Like.builder().like(true).posting(post2).user(user10).build());
        likeRepo.save(Like.builder().like(true).posting(post3).user(user9).build());
        likeRepo.save(Like.builder().like(true).posting(post3).user(user8).build());
        likeRepo.save(Like.builder().like(true).posting(post3).user(user7).build());
        likeRepo.save(Like.builder().like(true).posting(post2).user(user6).build());
        commentRepo.save(Comment.builder().writer(user5).posting(post1)
                .content("Such a great guide for anyone visiting Seoul! I can't wait to check out these spots during my next trip.")
                .build());
        commentRepo.save(Comment.builder().writer(user6).posting(post1)
                .content("I absolutely loved visiting the hidden gems in Seoul, especially the Euljiro area. Highly recommend it!")
                .build());
        commentRepo.save(Comment.builder().writer(user8).posting(post1)
                .content("Seoul really does offer the best of both worlds – history, culture, and modern attractions. This list is spot on!")
                .build());
        commentRepo.save(Comment.builder().writer(user8).posting(post2)
                .content("The food in Seoul is unbeatable! Loved the recommendation for Gwangjang Market. Can't wait to go back and try more!")
                .build());
        commentRepo.save(Comment.builder().writer(user10).posting(post2)
                .content("Thanks for the great suggestions! I’m planning a trip to Seoul soon, and this list will definitely help me plan my itinerary.")
                .build());
    }
    public void package1(){
        Package tourPack1 = Package.builder()
                //경북궁,롯데월드 아쿠아리움,롯데월드타워 서울스카이,가나돈까스의집,가담,가락골마산아구찜,고고스,리버시티수상스키장,레전드히어로즈 잠실롯데월드몰점,강남스테이힐(Gangnam Stay Hill)
                .listTourists("126508,2476731,2492348,2871024,2869760,2757617,2685296,767780,2946635,2574118")
                .price(300000)
                .title("서울의 과거와 현재를 걷다: 전통과 현대의 1박 2일 여행")
                .build();
        packageRepo.save(tourPack1);
        Schedule tour1FirstDay = Schedule.builder()
                .tourPackage(tourPack1)
                .day("Day1")
                .mainActivity("능인선원 방문 \n 대모산 도시자연공원 트래킹 \n 롯데월드 아쿠아리움 탐방" +
                        "\n 롯데월드타워 서울스카이 전망대 방문")
                .detail("첫째 날: 역사와 현대 도시의 조화\n" +
                        "오전\n" +
                        "\n" +
                        "경복궁 방문 (09:00~11:00)\n" +
                        "서울의 대표적인 역사 유적지인 경복궁에서 조선 시대의 궁궐을 탐방하며 한국의 전통 건축과 문화에 대해 알아봅니다. 왕궁 수문장 교대식도 볼 수 있습니다.\n" +
                        "점심 2. 가나돈까스의집에서 점심식사 (12:00~13:00)\n" +
                        "\n" +
                        "강남에서 유명한 수제 돈까스 전문점에서 바삭한 돈까스를 맛보며 즐거운 식사 시간을 가지세요.\n" +
                        "오후 3. 롯데월드 아쿠아리움 탐방 (14:00~16:00)\n" +
                        "\n" +
                        "롯데월드 아쿠아리움에서 다양한 해양 생물을 구경하며 서울 도심에서 바다의 신비로움을 느껴보세요.\n" +
                        "\n" +
                        "롯데월드타워 서울스카이 전망대 방문 (16:30~18:00)\n" +
                        "서울에서 가장 높은 전망대인 서울스카이에서 360도 파노라마 뷰를 감상하며 도시의 아름다움을 만끽합니다. 특히 해 질 무렵의 노을을 감상하기 좋은 시간대입니다.\n" +
                        "저녁 5. 가락골마산아구찜에서 저녁식사 (19:00~20:30)\n" +
                        "\n" +
                        "매콤하고 시원한 아구찜으로 저녁을 즐기며 여행 첫날을 마무리합니다.\n" +
                        "숙박 6. Gangnam Stay Hill에서 숙박 (21:00~)\n" +
                        "\n" +
                        "강남에 위치한 편안한 숙소에서 편안히 휴식하며 하루를 마무리합니다.")
                .build();
        scheduleRepo.save(tour1FirstDay);
        Schedule tour1SecondDay = Schedule.builder()
                .tourPackage(tourPack1)
                .day("Day2")
                .mainActivity("리버시티수상스키장 체험 \n 레전드히어로즈 잠실 롯데월드몰점에서 실내 스포츠 게임 즐기기")
                .detail("아침\n" +
                        "\n" +
                        "Gangnam Stay Hill에서 체크아웃 및 간단한 아침 (08:00~09:00)\n" +
                        "오전 2. 리버시티수상스키장 체험 (09:30~11:00)\n" +
                        "\n" +
                        "잠실 근처 한강에서 수상스키나 웨이크보드를 체험하며 아드레날린을 충전합니다.\n" +
                        "점심 3. 가담에서 점심식사 (12:00~13:30)\n" +
                        "\n" +
                        "퓨전 한식을 제공하는 가담에서 맛있는 식사를 즐기세요.\n" +
                        "오후 4. 레전드히어로즈 잠실 롯데월드몰점에서 실내 스포츠 게임 즐기기 (14:00~16:00)\n" +
                        "\n" +
                        "VR과 AR로 즐기는 실내 스포츠 테마파크에서 가족이나 친구와 함께 다양한 게임을 체험합니다.\n" +
                        "\n" +
                        "고고스 카페에서 커피 타임 (16:30~17:30)\n" +
                        "커피 한 잔과 함께 여유로운 오후를 즐기며 여행을 마무리합니다.")
                .build();
        scheduleRepo.save(tour1SecondDay);
        tourPack1.getSchedules().add(tour1FirstDay);
        tourPack1.getSchedules().add(tour1SecondDay);
    }

    public void package2(){
        Package tourPack2 = Package.builder()
                // DDP,남산서울타워,국립현대미술관 덕수궁,간송미술관(서울 보화각),가쯔야,기와,광화문석갈비,호텔
                .listTourists("2470006,126535,130173,130511,2652903,2029960,2670606,2652993,3337233")
                .price(300000)
                .title("서울의 예술과 전통을 담은 1박 2일 도심 여행")
                .build();
        packageRepo.save(tourPack2);
        Schedule tour2FirstDay = Schedule.builder()
                .tourPackage(tourPack2)
                .day("Day1")
                .mainActivity("DDP,국립현대미술관 덕수궁관, 간송미술관")
                .detail("오전\n" +
                        "\n" +
                        "DDP(동대문 디자인 플라자) 탐방 (09:00~11:00)\n" +
                        "서울의 현대적 랜드마크인 *동대문 디자인 플라자(DDP)*에서 독특한 건축미를 감상하고, 진행 중인 다양한 전시회를 둘러봅니다. 예술과 디자인의 조화를 느낄 수 있는 공간입니다.\n" +
                        "점심 2. 가쯔야에서 점심식사 (11:30~12:30)\n" +
                        "\n" +
                        "일본식 정통 돈카츠 전문점 가쯔야에서 바삭하고 촉촉한 돈카츠와 정통 일본 요리를 맛보며 든든한 식사를 즐깁니다.\n" +
                        "오후 3. 국립현대미술관 덕수궁관 탐방 (13:00~15:00)\n" +
                        "\n" +
                        "서울 도심 속에서 한국의 근대 미술을 만나볼 수 있는 국립현대미술관 덕수궁관에서 다양한 현대 미술 작품과 전시를 감상합니다. 근대 역사 속 예술적 흔적을 발견할 수 있는 흥미로운 시간이 될 것입니다.\n" +
                        "\n" +
                        "간송미술관(서울 보화각) 탐방 (15:30~17:00)\n" +
                        "한국 전통 예술의 보물창고인 간송미술관에서 한국의 문화재와 예술 작품을 감상합니다. 보화각은 전통 건축물로, 전통과 예술이 어우러진 독특한 공간입니다.\n" +
                        "저녁 5. 광화문석갈비에서 저녁식사 (18:00~19:30)\n" +
                        "\n" +
                        "한우와 갈비가 유명한 광화문석갈비에서 고급스러운 한식을 즐기며 서울 도심 속에서 전통적인 식사를 경험합니다." +
                        "\n" +
                        "숙박 6. 노보텔 앰배서더 서울 동대문 호텔 & 레지던스에서 수박 (20:00~)")
                .build();
        scheduleRepo.save(tour2FirstDay);
        Schedule tour2SecondDay = Schedule.builder()
                .tourPackage(tourPack2)
                .day("Day1")
                .mainActivity("남산서울타워 덕수궁, 정동길, 광화문 광장.")
                .detail("아침\n" +
                        "\n" +
                        "호텔 조식 및 체크아웃 (08:00~09:00)\n" +
                        "호텔에서 조식을 즐기고, 다음 일정을 준비합니다.\n" +
                        "오전 2. 남산서울타워 방문 (09:30~11:30)\n" +
                        "\n" +
                        "남산서울타워에 올라가 서울의 전경을 한눈에 내려다보세요. 케이블카를 타고 올라가는 과정도 즐거운 경험이 될 것입니다. 이곳에서 서울의 아름다운 도심 풍경을 느낄 수 있습니다.\n" +
                        "점심 3. 기와에서 점심식사 (12:00~13:30)\n" +
                        "\n" +
                        "한식 전문점 기와에서 정갈한 한식을 즐기며 전통의 맛을 느껴보세요. 한옥 느낌의 분위기 속에서 편안한 식사 시간을 보낼 수 있습니다.\n" +
                        "오후 4. 덕수궁 산책 및 정동길 탐방 (14:00~15:30)\n" +
                        "\n" +
                        "고요한 정원을 가진 덕수궁을 산책하며 고궁의 고즈넉한 분위기를 만끽하세요. 이후 주변의 정동길을 걸으며 서울 속 숨겨진 감성적인 공간들을 둘러볼 수 있습니다.\n" +
                        "마무리 5. 광화문 광장 자유 시간 (16:00~17:00)\n" +
                        "\n" +
                        "광화문 광장에서 자유롭게 산책하거나 기념사진을 찍으며 여행을 마무리합니다.")
                .build();

        scheduleRepo.save(tour2SecondDay);
        tourPack2.getSchedules().add(tour2FirstDay);
        tourPack2.getSchedules().add(tour2SecondDay);
    }

    public void package3(){
        Package tourPack3 = Package.builder()
                //남산골한옥마을,강남,창덕궁과 후원,광화문,옛맛서울불고기,갓덴스시 강남,감꽃당,개뿔,롯데호텔 L7 강남
                .listTourists("126747,264570,127642,126512,2642516,2685274,2622715,2848108,2548198")
                .price(500000)
                .title("서울 전통과 현대를 잇는 1박 2일 여행 코스")
                .build();
        packageRepo.save(tourPack3);
        Schedule tour3FirstDay = Schedule.builder()
                .tourPackage(tourPack3)
                .day("Day1")
                .mainActivity("남산골한옥마을, 옛맛서울불고기, 창덕궁과 후원, 감꽃당.")
                .detail("첫째 날: 전통의 매력 속으로\n" +
                        "오전\n" +
                        "\n" +
                        "남산골한옥마을 탐방 (09:00~12:00)\n" +
                        "서울의 중심에서 한국 전통 가옥을 체험할 수 있는 남산골한옥마을을 방문하여 한옥의 아름다움을 느끼고, 전통 놀이와 문화 체험을 즐깁니다.\n" +
                        "점심 2. 옛맛서울불고기에서 점심 (12:30~13:30)\n" +
                        "\n" +
                        "전통 불고기 맛집인 옛맛서울불고기에서 불고기의 깊은 맛을 체험합니다. 한국식 소불고기의 담백하고 달콤한 풍미를 느낄 수 있습니다.\n" +
                        "오후 3. 창덕궁과 후원 탐방 (14:00~17:00)\n" +
                        "\n" +
                        "유네스코 세계유산으로 지정된 창덕궁과 후원을 탐방하며, 조선 왕조의 궁궐과 그 후원을 산책합니다. 특히 후원의 자연과 조화를 이룬 아름다움은 꼭 경험해야 할 명소입니다.\n" +
                        "\n" +
                        "저녁 5. 감꽃당에서 전통 한식 저녁식사 (18:00~19:30)\n" +
                        "\n" +
                        "정갈하고 품격 있는 한식 레스토랑 감꽃당에서 전통적인 한식을 즐기며 하루의 피로를 풀어보세요. 한국의 사계절을 담은 음식들이 제공됩니다.\n" +
                        "숙박 6. 롯데호텔 L7 강남 체크인 및 휴식 (20:00~)\n" +
                        "\n" +
                        "롯데호텔 L7 강남에서 편안하게 숙박하며, 강남의 중심에서 여유로운 시간을 보낼 수 있습니다.")
                .build();
        scheduleRepo.save(tour3FirstDay);
        Schedule tour3SecondDay = Schedule.builder()
                .tourPackage(tourPack3)
                .day("Day2")
                .mainActivity("강남 거리 탐방, 갓덴스시 강남, 개뿔 카페 , 광화문 탐방")
                .detail("둘째 날: 현대적 감각과 미식 탐방\n" +
                        "아침\n" +
                        "\n" +
                        "호텔에서 조식 및 체크아웃 (08:00~09:00)\n" +
                        "L7 호텔에서 조식을 즐기고, 강남의 현대적인 분위기를 만끽하며 아침을 시작합니다.\n" +
                        "오전 2. 광화문 탐방 (09:30~11:30)\n" +
                        "\n" +
                        "광화문광장에서 이순신 장군 동상과 세종대왕 동상을 감상하고, 주변의 역사적 명소들을 돌아봅니다. 서울 도심의 역사적 중심지에서 서울의 전통과 현대를 동시에 느껴보세요.\n" +
                        "점심 3. 갓덴스시 강남에서 점심식사 (12:00~13:30)\n" +
                        "\n" +
                        "신선한 스시를 제공하는 갓덴스시 강남점에서 점심을 즐깁니다. 퀄리티 높은 재료로 만든 다양한 종류의 스시를 맛볼 수 있습니다.\n" +
                        "오후 4. 개뿔 카페에서 여유로운 티타임 (14:00~15:30)\n" +
                        "\n" +
                        "독특한 이름으로 유명한 개뿔 카페에서 분위기 있는 시간을 보내며 커피나 디저트를 즐기세요. 강남의 현대적인 감각이 묻어나는 힙한 공간입니다.\n" +
                        "마무리 5. 강남 거리 탐방 및 쇼핑 (16:00~17:00)\n" +
                        "\n" +
                        "강남의 세련된 거리와 트렌디한 상점들을 둘러보며 자유롭게 쇼핑을 즐깁니다. 카페에서 커피 한 잔과 함께 강남의 현대적이고 활기찬 분위기를 느껴보세요.")
                .build();

        scheduleRepo.save(tour3SecondDay);
        tourPack3.getSchedules().add(tour3FirstDay);
        tourPack3.getSchedules().add(tour3SecondDay);
    }
    public void fixBooking(){
        UserEntity user2 = userRepo.findById(2L).orElseThrow();
        UserEntity user3 = userRepo.findById(3L).orElseThrow();
        UserEntity user4 = userRepo.findById(4L).orElseThrow();

        Package tour1 = packageRepo.findById(1L).orElseThrow();
        Package tour2 = packageRepo.findById(2L).orElseThrow();
        Package tour3 = packageRepo.findById(3L).orElseThrow();

        BookTours book1 = BookTours.builder()
                .tourPackage(tour1)
                .numOfPeople(3)
                .customer(user2)
                .departureDay(LocalDate.of(2024, 10,30))
                .status(BookTours.Status.Booking_successful)
                .coupon(BookTours.Coupon.ZERO)
                .payment(tour1.getPrice()*3)
                .build();
        bookToursRepo.save(book1);

        BookTours book2 = BookTours.builder()
                .tourPackage(tour1)
                .numOfPeople(6)
                .customer(user3)
                .departureDay(LocalDate.of(2024, 11,28))
                .status(BookTours.Status.Cancel_booking)
                .coupon(BookTours.Coupon.TWENTY)
                .payment(tour1.getPrice()*80/100*6)
                .build();
        bookToursRepo.save(book2);

        BookTours book3 = BookTours.builder()
                .tourPackage(tour2)
                .numOfPeople(10)
                .customer(user2)
                .departureDay(LocalDate.of(2024, 12,9))
                .status(BookTours.Status.Confirmed)
                .rating(4)
                .coupon(BookTours.Coupon.THIRTY)
                .payment(tour2.getPrice()*70/100*10)
                .build();
        bookToursRepo.save(book3);

        BookTours book4 = BookTours.builder()
                .tourPackage(tour3)
                .numOfPeople(2)
                .customer(user2)
                .departureDay(LocalDate.of(2024, 12,9))
                .status(BookTours.Status.Cancel_booking)
                .coupon(BookTours.Coupon.ZERO)
                .payment(tour3.getPrice()*2)
                .build();
        bookToursRepo.save(book4);

        BookTours book5 = BookTours.builder()
                .tourPackage(tour1)
                .numOfPeople(2)
                .customer(user3)
                .departureDay(LocalDate.of(2024, 12,11))
                .status(BookTours.Status.Payment_successful)
                .coupon(BookTours.Coupon.ZERO)
                .payment(tour1.getPrice()*2)
                .build();
        bookToursRepo.save(book5);
        BookTours book6 = BookTours.builder()
                .tourPackage(tour2)
                .numOfPeople(1)
                .customer(user4)
                .departureDay(LocalDate.of(2024, 12,11))
                .status(BookTours.Status.Confirmed)
                .rating(5)
                .coupon(BookTours.Coupon.ZERO)
                .payment(tour2.getPrice())
                .build();
        bookToursRepo.save(book6);
        BookTours book7 = BookTours.builder()
                .tourPackage(tour3)
                .numOfPeople(20)
                .customer(user4)
                .departureDay(LocalDate.of(2024, 12,15))
                .status(BookTours.Status.Confirmed)
                .rating(5)
                .coupon(BookTours.Coupon.FIFTY)
                .payment(tour3.getPrice()*50/100*20)
                .build();
        bookToursRepo.save(book7);

    }
}
