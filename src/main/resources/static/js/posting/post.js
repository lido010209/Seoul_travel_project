const token = localStorage.getItem("token");

const pathSplit = location.pathname.split("/");
const postId = pathSplit[pathSplit.length - 1];
let writerId;
let userId;

const postTitle = document.getElementById("post-title");
const postContent = document.getElementById("post-content");
const writerImage = document.getElementById("writer-image");
const writerName = document.getElementById("writer-name");
const numOfLike = document.getElementById("post-like");
const numOfComment = document.getElementById("post-comment");
const postBody = document.getElementById("post-body");
const postImageContainer = document.getElementById("post-image-container");

let numlike;
let numComment;

const editPost = document.getElementById("edit-post");
const editBtn = document.getElementById("edit-post-btn")
const formEdit = document.getElementById("form-edit");
const editTitle = document.getElementById("edit-title");
const editContent = document.getElementById("edit-content");
const currentUserName = document.getElementById("current-user-name");

//Comment
const commentList = document.getElementById("comment-list");
const commentWriter = document.getElementById("comment-writer-name");
const commentContent = document.getElementById("comment-content");
const commentPost = document.getElementById("comment-post");
const commentCreateContent = document.getElementById("comment-create-content");


//heart + down arrow handler
const heart = document.getElementById("heart");
const down = document.getElementById("down");

if (!token) {
    displayPost(null);
    displayComment(null);

    commentPost.addEventListener("submit", e => {
        e.preventDefault();
        alert("로그인 필요합니다!!!");
        location.href=`/travel/login`;
    })
    heart.addEventListener("click",e=>{
        e.preventDefault();
        alert("로그인 필요합니다!!!");
        location.href=`/travel/login`;
    })
    down.addEventListener("click", () => {
        if (commentList.style.display === "block") {
            commentList.style.display = "none";
        } else {
            commentList.style.display = "block";
        }
    })
} else {
    fetch("/users", {
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-type": "application/json",
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            else throw Error(response.statusText);
        })
        .then(user => {
            
            displayPost(user);
            displayComment(user);
            commentPost.addEventListener("submit", e => {
                e.preventDefault();
            
                const commentData = {
                    content: commentCreateContent.value,
                    postingId: postId
                }
            
                fetch("/comments", {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-type": "Application/json"
                    },
                    body: JSON.stringify(commentData),
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        }
                        else throw Error(response.statusText);
                    })
                    .then(comment => {
                        commentList.style.display = "block";
                        document.getElementById("comment-create-content").value=``;
                        const div= document.createElement("div");
                        div.className="mx-3 my-3";
                        div.innerHTML=
                        `
                            
                                <div class="d-flex justify-content-between align-items-center ps-3 pe-3">
                                    <div class="d-flex align-items-center">
                                        <img src="${comment.writerImg}" class="rounded-circle border border-secondary me-2" id="writer-image" style="height: 40px; width: 40px;">
                                        <a class="navbar-brand fw-bold fs-5" aria-current="page" href="#" id="comment-writer-name">${comment.writer}</a>
                                    </div>
                                    <button id="delete-comment-btn${comment.id}" class="d-none btn btn-outline-danger">삭제</button>
                                </div>
                                
                                <p class="m-3" id="comment-content${comment.id}">${comment.content}</p>
                                <button id="edit-comment-btn${comment.id}" class="btn btn-outline-primary btn-sm">수정</button>
                                <hr class="ps-3 pe-3">
                            
                        `
                        commentList.appendChild(div);
                        numOfComment.innerText=numComment+1;
                        numComment = numComment+1;
                        editCommentFuc(comment);
                    })
            });
            
            fetch(`/like/${postId}`, {
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-type": "Application/json",
                },
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    else throw Error(response.statusText);
                })
                .then(json=>{
                    if (json ===true){
                        heart.style.color = "red";
                        window.addEventListener("beforeunload", e=>{
                            e.preventDefault();
                            if (heart.style.color === "gray"){
                                fetch(`/like/${postId}`, {
                                    method: "DELETE",
                                    headers: {
                                        "Authorization": `Bearer ${token}`,
                                        "Content-type": "Application/json",
                                    },
                                })
                            }
                        })
                    } 
                    else {
                        heart.style.color = "gray";
                        window.addEventListener("beforeunload", e=>{
                            e.preventDefault();
                            if (heart.style.color === "red"){
                                fetch(`/like/${postId}`, {
                                    method: "POST",
                                    headers: {
                                        "Authorization": `Bearer ${token}`,
                                        "Content-type": "Application/json",
                                    },
                                })
                            }
                        })
                    }
                    
                })
            
            
            heart.addEventListener("click", () => {
                if (heart.style.color === "gray") {
                    numOfLike.innerText=numlike+1;
                    numlike=numlike+1;
                    heart.style.color = "red";
                    
                } else if (heart.style.color === "red") {
                    numOfLike.innerText=numlike-1;
                    numlike=numlike-1;
                    heart.style.color = "gray";
                }
            })
                    
        })
        .catch(error => console.error("Error:", error));
    
}


function displayPost(user){
    fetch(`/posting-view/${postId}`, {
        method: "GET",
        // headers: {
        //     "Authorization": `Bearer ${token}`,
        //     "Content-type": "Application/json"
        // }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            else {
                alert("이 블로그 존재하지 않습니다!!!")
                location.href=`/travel/post`;
            }
        })
        .then(json => {
            const imageList = json.images;
    
            writerImage.src = json.writerAvatar;
            postTitle.innerText = json.title;
            writerName.innerText = json.writer;
            currentUserName.innerText = json.writer;
            
    
            numOfLike.innerText = json.numOfLike === null ? 0 : json.numOfLike;
            numlike = json.numOfLike === null ? 0 : json.numOfLike;
            numOfComment.innerText = json.numOfComment === null ? 0 : json.numOfComment;
            numComment = json.numOfComment === null ? 0 : json.numOfComment;
            if (imageList.length ===0) {
                for (let i = 0; i < 3; i++) {
                    imageList.push(`/static/visual/posting/image${Math.floor(Math.random() * 9) + 1}.png`);
                }
            }
            else if (imageList.length <3) {
                for (let i = 0; i < 3-imageList.length; i++) {
                    imageList.push(`/static/visual/posting/image${Math.floor(Math.random() * 9) + 1}.png`);
                }
            }

            imageList.forEach(url => {
                postImageContainer.insertAdjacentHTML("beforeend", `
                    <div class="col-lg-4 col-md-6 col-12 mb-4 d-flex justify-content-center">
                        <img src="${url}" class="img-fluid" style="height: 230px;">
                    </div>
                `)
            });
            postContent.innerHTML = `<p class="fs-5">${json.content}</p>`;

            if (user && json.writerId === user.id) {
                
                editBtn.style.display = "inline";
                editTitle.value = json.title;
                editContent.value = json.content;
                editBtn.addEventListener("click", function () {
                    editPost.classList.remove("d-none");

                    formEdit.addEventListener("submit", e => {
                        e.preventDefault();
                
                        const dataEdit = {
                            title: editTitle.value,
                            content: editContent.value
                        };
                        fetch(`/posting/${postId}`, {
                            method: "PUT",
                            headers: {
                                "Authorization": `Bearer ${token}`,
                                "Content-type": "Application/json",
                            },
                            body: JSON.stringify(dataEdit)
                        })
                            .then(response => {
                                if (response.ok) { return response.json() }
                                else {
                                    return response.text().then(text => { alert(text) })
                                }
                            })
                            .then(post => {
                                document.getElementById("edit-title").value=post.title;
                                document.getElementById("edit-content").value=post.content;
                                editPost.classList.add("d-none");
                                postTitle.innerText=post.title;
                                postContent.innerText=post.content;
                                alert("변경 사항이 저장되었습니다^^")
                            })
                    })
                })
            
                document.getElementById("close-post").addEventListener("click", e => {
                    e.preventDefault();
                    editPost.classList.add("d-none");
                })
                const deletePostBtn = document.getElementById("delete-post-btn");
                deletePostBtn.classList.remove("d-none");
                deletePostBtn.addEventListener("click", e=>{
                    e.preventDefault();
                    const confDel=confirm("이 블로그를 삭제합니까?");
                    if (confDel){
                        fetch(`/posting/${postId}`, {
                            method: "DELETE",
                            headers: {
                                "Authorization": `Bearer ${token}`,
                            }
                        }).then(response=>{
                            if (response.ok){
                                alert("삭제 와료되었습니다!!!")
                                location.href="/travel/profile";
                            }
                        })
                    }
                })
            }
        })
}

function displayComment(user){
    fetch(`/comments/list/${postId}`, {
        method: "GET",
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            else throw Error(response.statusText);
        })
        .then(json => {
            json.forEach(comment => {
                commentList.insertAdjacentHTML("beforeend",
                    `
                <div class="mx-3 my-3">
                    <div class="d-flex justify-content-between align-items-center ps-3 pe-3">
                        <div class="d-flex align-items-center">
                            <img src="${comment.writerImg}" class="rounded-circle border border-secondary me-2" id="writer-image" style="height: 40px; width: 40px;">
                            <a class="navbar-brand fw-bold fs-5" aria-current="page" href="#" id="comment-writer-name">${comment.writer}</a>
                        </div>
                        <button id="delete-comment-btn${comment.id}" class="d-none btn btn-outline-danger">삭제</button>
                    </div>
                    
                    <p class="m-3" id="comment-content${comment.id}">${comment.content}</p>
                    <button id="edit-comment-btn${comment.id}" class="d-none btn btn-outline-primary btn-sm">수정</button>
                    <hr class="ps-3 pe-3">
                </div>
                `
                )
                if (user && comment.writerId===user.id){
                    editCommentFuc(comment);
                }
            })
    })
}

function editCommentFuc(comment){
    
        document.getElementById(`delete-comment-btn${comment.id}`).classList.remove("d-none");
        document.getElementById(`delete-comment-btn${comment.id}`).addEventListener("click", e=>{
            e.preventDefault();
            const conf= confirm("댓글 삭제하십니까?")
            if (conf){
                fetch(`/comments/${comment.id}`, {
                    method: "DELETE",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                    }
                }).then(response=>{
                    if (response.ok){
                        alert("삭제 완료되었습니다!!!")
                        location.reload()
                    }
                })
            }
        })

        document.getElementById(`edit-comment-btn${comment.id}`).classList.remove("d-none");
        document.getElementById(`edit-comment-btn${comment.id}`).addEventListener("click",e=>{
            e.preventDefault();
            const editComment = document.getElementById("edit-comment");
            editComment.classList.remove("d-none");
            document.getElementById("close-comment").addEventListener("click", e=>{
                e.preventDefault();
                editComment.classList.add("d-none");
            })
            const editCommentContent = document.getElementById("edit-comment-content");
            editCommentContent.value=comment.content;
            document.getElementById("form-edit-comment").addEventListener("submit", e=>{
                e.preventDefault();
                fetch(`/comments/${comment.id}`, {
                        method: "PUT",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-type": "Application/json"
                        },
                        body: JSON.stringify({content: editCommentContent.value})
                    })
                        .then(response => {
                            if (response.ok) {
                                return response.json();
                            } else throw Error(reponse.statusText);
                        })
                        .then(comment => {
                            editCommentContent.value= comment.content;
                            editComment.classList.add("d-none");
                            document.getElementById(`comment-content${comment.id}`).innerText=comment.content;
                            
                        });
            })
        })
        
    
}