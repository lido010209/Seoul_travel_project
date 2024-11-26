const token = localStorage.getItem("token");
if (!token){
    location.href = "/travel/login";
}

const editBasicInfo = document.getElementById("edit-basic-info");
const editProfile = document.getElementById("edit-profile");
const changePw = document.getElementById("change-pw");


const fullname = document.getElementById("name");
const email = document.getElementById("email");
const phone =document.getElementById("phone");
const username = document.getElementById("username");
const avatar = document.getElementById("avatar");

fetch("/users", {
    headers: {
        "Authorization": `Bearer ${token}`,
        "Content-type": "Application/json",
    }
}).then(response=>{
    if(response.ok){return response.json()}
    else{
        return response.text().then(text=>{alert(text)})
    }
}).then(user=>{
    displayUser(user);
})
function displayUser(user){
    fullname.value = user.name;
    email.value = user.email;
    phone.value = user.phone;
    username.innerText = user.username;
    avatar.src = user.profileImg;
}


editBasicInfo.addEventListener("click", e=>{
    e.preventDefault();
    editProfile.classList.remove("d-none");
    document.getElementById("close-profile").addEventListener("click", e=>{
        e.preventDefault();
        editProfile.classList.add("d-none");
    })

    document.getElementById("form-edit").addEventListener("submit", e=>{
        e.preventDefault();
        const dataEdit = {
            email: email.value,
            phone:phone.value,
            name: fullname.value,
        }
        fetch("/users", {
            method: "PUT",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": "Application/json",
            }, 
            body: JSON.stringify(dataEdit),
        }).then(response=>{
            if(response.ok){return response.json()}
            else{
                return response.text().then(text=>{alert(text)})
            }
        }).then(user=>{
            fullname.value = user.name;
            email.value = user.email;
            phone.value = user.phone;
            editProfile.classList.add("d-none");
            alert("변경 사항이 저장되었습니다^^");
        })
    })
})


const avaImage = document.getElementById("avatar-image");
avaImage.addEventListener("submit", e=>{
    e.preventDefault();
    const formData = new FormData(avaImage); 
    fetch("/users/avatar", {
        method:"PUT",
        headers: {
            "Authorization": `Bearer ${token}`,
        }, 
        body: formData,
    }).then(response=>{
        if (response.ok){return response.json()}
        else{alert("Fail to upload file");}
    }).then(json =>{
        avatar.src= json.profileImg;
        location.reload();
    })
})

const btnChangePw = document.getElementById("btn-changePw");
btnChangePw.addEventListener("click", e=>{
    e.preventDefault();
    changePw.classList.remove("d-none");
    document.getElementById("close-changePw").addEventListener("click", e=>{
        e.preventDefault();
        changePw.classList.add("d-none");
    })
    document.getElementById("form-pw").addEventListener("submit", e=>{
        e.preventDefault();
        const newPw = document.getElementById("new-password");
        const oldPw = document.getElementById("old-password");
        const pwCheck = document.getElementById("pwCheck");
        if (newPw.value!==pwCheck.value){
            alert("Passwords do not match!!!")
        }
        fetch(`/users/password?oldPw=${oldPw.value}&newPw=${newPw.value}`,{
            method:"PUT",
            headers: {
                "Content-type": "Application/json",
                "Authorization": `Bearer ${token}`,
            }, 
        }).then(response=>{
            if (response.ok){
                alert("비밀번호 변경이 성공합니다!")
                changePw.classList.add("d-none");
                return response.json();
            }
            else{alert("Please check your password again!!!");}
        })
    })
})

const listPost = document.getElementById("list-post");
function allPost(posting){
    const div = document.createElement("div");
    div.className="w-100 mb-1 d-flex";
    div.innerHTML=
    `
    <a href="/travel/post/${posting.id}" class="d-flex text-decoration-none text-dark align-items-center">
        <div class="col-12 col-lg-9 border rounded p-3">
            <div class="d-flex justify-content-between">
                <div class="d-flex align-items-center mb-2">
                    <img src="${posting.writerAvatar}" class="rounded-circle border border-secondary" style="height: 40px;">
                    <span>${posting.writer}</span>
                </div>
            </div>
                                    
            <h4 class="fw-bold">${posting.title}</h4>
            <div class="">
                ${posting.content.slice(0,100)}...
            </div>
        </div>
        <div class="d-none d-lg-flex col-3 rounded p-3">
            <img src="/static/visual/posting/image${Math.floor(Math.random() * 4) + 1}.png" class="img-fluid">
        </div>
    </a>
    `;
    listPost.appendChild(div);
}

fetch("/posting", {
    headers: {
        "Authorization": `Bearer ${token}`,
        "Content-type": "Application/json",
    }
}).then(response=>{
    if (response.ok){return response.json();}
    else{return response.text().then(text=>{alert(text)})}
}).then(json=>{
    json.forEach(posting=>{
        allPost(posting);
    })
})
