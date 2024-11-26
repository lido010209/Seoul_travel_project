// const token = localStorage.getItem("token");
// if (!token) {
//     location.href = "/travel/login";
// }

//post List
const postBody = document.getElementById("post-body");
let writerId;

fetch("/posting-view", {
    method: "GET",
    // headers: {
    //     "Authorization": `Bearer ${token}`
    // }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else throw Error(response.statusText);
    })
    .then(json => {
        json.forEach(post => {
            let postId = post.id;
            let images = post.images;
            // let numOfLike = post.numOfLike == null ? 0 : post.numOfLike;
            // let numOfComment = post.numOfComment == null ? 0 : post.numOfComment;
            let image_url = images.length === 0 ? `/static/visual/posting/image${Math.floor(Math.random() * 9) + 1}.png` : `${images[0]}`;
            // let writerId = post.writerId;

                    // Insert HTML after user data is retrieved
                    postBody.insertAdjacentHTML("beforeend", `
                    <div class="d-flex align-items-center w-100 flex-wrap">
                        <div class="col-lg-9 col-sm-12 pe-3">
                            <div class="d-flex align-items-center">
                                <div><img src="${post.writerAvatar}"
                                        class="rounded-circle border border-secondary profile-image"> </div>
                                <a class="navbar-brand  m-3 fs-5" aria-current="page">${post.writer}</a>
                            </div>
                            <a class="postTitle text-dark fw-bold text-decoration-none fs-4">${post.title}</a>
                            <div class="postContent fs-6 limited-text">${post.content}</div>
                            
                        </div>
                        <div class="col-lg-3 col-sm-12 d-flex justify-content-center">
                            <img src="${image_url}" class="img-fluid img-thumbnail post-image">
                        </div>
                        <div class="w-100 d-flex my-1" id="placeBtn">
                            <a class=" btn-detail btn btn-primary btn-sm text-white text-decoration-none" href="/travel/post/${postId}">자세히 보기</a>
                        </div>
                        <hr class="w-100">
                    </div>
                    `);
                })
        });



