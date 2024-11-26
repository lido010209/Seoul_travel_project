// console.log(localStorage.getItem("token"));
if (localStorage.getItem("token")) {
    location.href= "/travel/profile";
}

const usernameLogin = document.getElementById("username");
const passwordLogin = document.getElementById("password");
const pwCheckLogin = document.getElementById("pwCheck");
const email = document.getElementById("email");
const phone = document.getElementById("phone");
const fullname = document.getElementById("name");

// 해당 데이터를 모아서 JSON으로 반환
// JSON 데이터를 서버로 전송
// 응답

const formLogin = document.getElementById("form-create");

formLogin.addEventListener("submit", e=>{
    e.preventDefault();

    // Check password match
    if (passwordLogin.value !== pwCheckLogin.value) {
        alert("Passwords do not match!!!");
        return;
    }

    //보낸 데이터를 정리해둔다.
    const dataLogin = {
        username: usernameLogin.value,
        password: passwordLogin.value,
        email: email.value,
        phone: phone.value,
        name: fullname.value,
    }
    //2.fetch 요청을 보낸다.
    fetch("/users", {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify(dataLogin),
    })
    .then (response => {
        if (response.ok){
            alert("Sign in successful!!!\nPlease login to your account^^");
            location.href= "/travel/login";
            return response.json();
        } 
        else {
            return response.text().then(text=>
                alert(text)
            )
        }
    })
    // 응답을 담긴 JWT 를 저장한다
    // .then(json => {
    //     // localStorage.setItem("user", )
        
    // })
    .catch(e =>{
        alert(e.message)
    })
})