if (localStorage.getItem("token")) {
    location.href= "/travel/home";
}

const usernameLogin = document.getElementById("username");
const passwordLogin = document.getElementById("password");

// 해당 데이터를 모아서 JSON으로 반환
// JSON 데이터를 서버로 전송
// 응답

const formLogin = document.getElementById("form-login");

formLogin.addEventListener("submit", e=>{
    e.preventDefault();

    //보낸 데이터를 정리해둔다.
    const dataLogin = {
        username: usernameLogin.value,
        password: passwordLogin.value,

    }
    //2.fetch 요청을 보낸다.
    fetch("/token/issue", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(dataLogin),
    })
    .then(response => {
        if (response.ok){
            return response.json();
        } 
        else {
            return response.text().then(text => {
                // console.log(text);
                alert(text);
            });
        }
    })
    // 응답을 담긴 JWT 를 저장한다
    .then(json => {
        localStorage.setItem("token", json.token);
        location.href="/travel/home";
        // console.log(json);
        // if (dataLogin.username==="luna010209"){
        //     location.href="/admin/message";
        // } else{
        //     location.href="/travel/message";
        // }
    })
    .catch(e =>{
        alert(e.message)
    })
})

const naverLogin= document.getElementById("naver");

naverLogin.addEventListener("click", e=>{
    // e.preventDefault();
    
})

fetch("/token/oauth", {
    headers: {
        "Content-type": "Application/json",
    }
}).then(response=>{
    if (response.ok){return response.json();}
    else {}
}).then(json=>{
    localStorage.setItem("token", json.token);
    // localStorage.setItem("authorities", json.stringAuthorities);
    location.href="/travel/home";
})