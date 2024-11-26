const navbar = document.getElementById("navbar");
// fetch("/token/oauth", {
//     method: "GET",
// }).then(response=>{
//     if (response.ok) {
//         localStorage.setItem("token", response.json().token);
//     } else if (response.status===403){
//         localStorage.setItem("token", null);
//     }
// })
const tokenNavbar = localStorage.getItem("token");
if (tokenNavbar) {login()}
else {nonLogin()}

function nonLogin(){
    navbar.innerHTML =
`
                <div class="dropdown navbar-menu">
                    <button id="btn-menu" class="navbar-toggler" type="button" data-bs-toggle="dropdown"
                        data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false"
                        aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <ul id="list-menu" class="dropdown-menu navbar-menu-list" aria-labelledby="dropdownMenuButton" style="width: 150px;">
                        <li><a class="dropdown-item" href="/travel/package/1"><i class="fa-solid fa-globe"></i> 여행코스</a></li>
                        <li><a class="dropdown-item" href="/travel/tour"><i class="fa-solid fa-earth-americas"></i> 관광지</a></li>
                        <li><a class="dropdown-item" href="/travel/post"><i class="fa-solid fa-blog"></i> 블로그</a></li>
                        <li><a class="dropdown-item" href="/travel/map"><i class="fa-solid fa-map"></i> 지도</a></li>
                    </ul>
                </div>

                <a class="navbar-brand fs-2" href="/travel/home"><img src="/static/visual/home/logo.png" class="img-fluid" style="height: 50px;"></a>
                <div class="collapse navbar-collapse mx-4 " id="navbarTogglerDemo03">
                    <div class="d-flex w-100 justify-content-between align-items-center">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0 ">
                            <li class="nav-item mx-3">
                                <a class="navbar-brand active" aria-current="page" href="/travel/package/1">여행 코스</a>
                            </li>
                            <li class="nav-item mx-3">
                                <a class="navbar-brand" href="/travel/tour">관광지</a>
                            </li>
                            <li class="nav-item mx-3">
                                <a class="navbar-brand" href="/travel/post">블로그</a>
                            </li>
                        </ul>
                        <div class="d-flex align-items-center justify-content-center">
                            <a href="/travel/map" target="_blank"><img src="/static/visual/home/map.png" class="ms-4" style="height: 40px; width: 40px;"></a>
                        </div>
                    </div>
                </div>
                <div class="d-none d-sm-flex justify-content-end">
                    <a href="/travel/login" target="_blank" class="btn btn-light me-2">로그인</a>
                    <a href="/travel/signup" target="_blank" class="btn btn-light">회원가입</a>
                </div>
`;
}

function login(){
    navbar.innerHTML =
`
                <div class="dropdown navbar-menu">
                    <button id="btn-menu" class="navbar-toggler" type="button" data-bs-toggle="dropdown"
                        data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false"
                        aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <ul id="list-menu" class="dropdown-menu navbar-menu-list" aria-labelledby="dropdownMenuButton" style="width: 150px;">
                        <li><a class="dropdown-item" href="/travel/package/1"><i class="fa-solid fa-globe"></i> 여행코스</a></li>
                        <li><a class="dropdown-item" href="/travel/tour"><i class="fa-solid fa-earth-americas"></i> 관광지</a></li>
                        <li><a class="dropdown-item" href="/travel/post"><i class="fa-solid fa-blog"></i> 블로그</a></li>
                        <li><a class="dropdown-item" href="/travel/map"><i class="fa-solid fa-map"></i> 지도</a></li>
                    </ul>
                </div>

                <a class="navbar-brand fs-2" href="/travel/home"><img src="/static/visual/home/logo.png" class="img-fluid" style="height: 50px;"></a>
                <div class="collapse navbar-collapse mx-4 " id="navbarTogglerDemo03">
                    <div class="d-flex w-100 justify-content-between align-items-center">
                        <ul class="navbar-nav  me-auto mb-2 mb-lg-0 ">
                            <li class="nav-item mx-3">
                                <a class="navbar-brand active" aria-current="page" href="/travel/package/1">여행 코스</a>
                            </li>
                            <li class="nav-item mx-3">
                                <a class="navbar-brand" href="/travel/tour">관광지</a>
                            </li>
                            <li class="nav-item mx-3">
                                <a class="navbar-brand" href="/travel/post">블로그</a>
                            </li>
                            <li id="manage-booking" class="d-none nav-item mx-3">
                                <a class="navbar-brand" href="/admin/booking">예약 관리</a>
                            </li>
                        </ul>
                        <div id="manage-mes" class="d-none d-flex align-items-center justify-content-center position-relative">
                            <a href="/admin/message"><i class="fa-solid fa-comments"></i></a>
                            <span id="countUser" class="d-none position-absolute" style="top: -1px; right: -7px; background: red; color: white; border-radius: 50%; padding:1px 6px; font-size: 10px;">3</span>
                            <span id="mark-misscall-admin" class="d-none position-absolute top-0 start-100 translate-middle badge border border-light rounded-circle bg-danger p-2"><span class="visually-hidden">unread messages</span></span>
                        </div>
                        <div id="call-icon" class="d-flex align-items-center justify-content-center position-relative">
                            <a href="#" class="p-1"><img src="/static/visual/call.png" class="ms-4 img-fluid" style="height: 40px; width: 40px; border-radius: 50%;"></a>
                            <span id="mark-misscall" class="d-none position-absolute top-0 start-100 translate-middle badge border border-light rounded-circle bg-danger p-2"><span class="visually-hidden">unread messages</span></span>
                        </div>
                        <div class="d-flex align-items-center justify-content-center">
                            <a href="/travel/map" target="_blank"><img src="/static/visual/home/map.png" class="ms-4" style="height: 40px; width: 40px;"></a>
                        </div>
                    </div>
                </div>
                <div class="dropdown">
                    <div id="profileButton" type="button" data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false">
                        <img id="avatar" src="/static/visual/user.png"
                            class="rounded-circle border border-secondary" style="height: 40px; width: 40px;">
                    </div>
                    <div class="bg-white rounded position-absolute end-0 mt-3 me-1 dropdown-menu" id="profile-dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <div class="d-flex align-items-center mt-1">
                            <i class="fa-solid fa-circle-user fa-lg mx-2"></i>
                            <p id="name" class=" mb-0 fs-6 text-center"></p>
                        </div>
                        <hr>
                        <div class="d-flex align-items-center mb-3">
                            <i class="fa-solid fa-user ms-2"></i> 
                            <a class="dropdown-item" href="/travel/profile">프로필</a>
                        </div>
                        <div class="d-flex align-items-center mb-3">
                            <i class="fa-solid fa-list-check ms-2"></i> 
                            <a class="dropdown-item" href="/travel/my-booking">내 예약</a>
                        </div>
                        <div id="logout" class="d-flex align-items-center ">
                            <i class="fa-solid fa-right-from-bracket ms-2"></i> 
                            <a class="dropdown-item" href="#">로그아웃</a>
                        </div>
                    </div>
                </div>
`;


document.getElementById("logout").addEventListener("click", e=>{
    e.preventDefault();
    localStorage.removeItem("token");
    localStorage.removeItem("authorities");
    location.href = "/travel/home";
})

const profileButton = document.getElementById('profileButton');
const dropdownMenu = document.getElementById('profile-dropdown-menu');
profileButton.addEventListener('click', function () {
    if (dropdownMenu.style.display == 'block') {
        dropdownMenu.style.display = 'none';
    } else {
        dropdownMenu.style.display = 'block';
    }
})

// const videoDis = document.getElementById("start-call");
// const callType= document.getElementById("call-type");
// videoDis.addEventListener('click', function () {
//     if (callType.classList.contains("d-none")) {
//         callType.classList.remove("d-none");
//     } else {
//         callType.classList.add("d-none");
//     }
// })

window.addEventListener('click', function (e) {
    if (!profileButton.contains(e.target) && !dropdownMenu.contains(e.target)) {
        dropdownMenu.style.display = 'none';
    }
    // if (!videoDis.contains(e.target) && !callType.contains(e.target)) {
    //     callType.classList.add("d-none");
    // }
});



fetch("/users", {
    headers: {
        "Authorization": `Bearer ${tokenNavbar}`,
        "Content-type": "Application/json",
    }
}).then(response=>{
    if(response.ok){return response.json()}
    else{
        return response.text().then(text=>{alert(text)})
    }
}).then(user=>{
    localStorage.setItem("authorities", user.stringAuthorities);
    if (user.stringAuthorities.includes("ROLE_ADMIN")){
        const manageBooking= document.getElementById("manage-booking");
        manageBooking.classList.remove("d-none");
        document.getElementById("manage-mes").classList.remove("d-none");
        document.getElementById("call-icon").classList.add("d-none");
        fetch("/messages/admin/count-user", {
            headers: {
                "Authorization": `Bearer ${tokenNavbar}`,
                "Content-type": "Application/json",
            }
        }).then(response=>{
            if (response.ok){
                return response.json();
            }
            else {return response.text().then(text=>{alert(text)})}
        }).then(json=>{
            const countUser= document.getElementById("countUser");
            if (json!==0) countUser.classList.remove("d-none");
            countUser.innerText=json;
        })

        fetch("/call/miss-call/admin", {
            headers: {
                "Authorization": `Bearer ${tokenNavbar}`,
                "Content-type": "Application/json",
            }
        }).then(response=>{
            if(response.ok){return response.json()}
            else{
                return response.text().then(text=>{alert(text)})
            }
        }).then(json=>{
            if (json===true){
                // document.getElementById("mark-misscall").classList.remove("d-none");
                document.getElementById("mark-misscall-admin").classList.remove("d-none");
            }     
        })
    } else {
        fetch("/call/miss-call", {
            headers: {
                "Authorization": `Bearer ${tokenNavbar}`,
                "Content-type": "Application/json",
            }
        }).then(response=>{
            if (response.ok){return response.json();}
            else {response.text().then(text=>{alert(text)})}
        }).then(call=>{
            if(call.id!==null){
                document.getElementById("mark-misscall").classList.remove("d-none");
                document.getElementById("call-icon").addEventListener("click", e=>{
                        e.preventDefault();
                        fetch(`/call/${call.id}`, {
                            method: "DELETE",
                            headers: {
                                "Authorization": `Bearer ${tokenNavbar}`,
                            }
                        })
                    })
            }      
        })
    }
    document.getElementById("avatar").src=user.profileImg;
    document.getElementById("name").innerText=user.name;
})

}


{/* <div id="call-type" class="d-none position-absolute rounded top-100 end-0 ps-1 pe-1 mt-3 bg-white" style="width:120px;">
                                <a id="voiceCall" href="#" class="text-decoration-none"><i class="fa-solid fa-microphone"></i> Voice call</a><br>
                                <a id="videoCall" href="#" class="text-decoration-none"><i class="fa-solid fa-video"></i> Video call</a>
                            </div> */}


