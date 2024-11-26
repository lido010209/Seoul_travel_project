const token = localStorage.getItem("token");
if (!token) {
    location.href = "/travel/login";
}

//form create 1
const post_title = document.getElementById("post-title");
const post_content = document.getElementById("post-content");
const formPost_1 = document.getElementById("post-create-1");
const formPost_2 = document.getElementById("post-create-2");
const nextBtn = document.getElementById("next");

//form create2
const completeForm = document.getElementById('CompleteForm');
const imageForm1 = document.getElementById("ImageForm1");
const imageForm2 = document.getElementById("ImageForm2");
const imageForm3 = document.getElementById("ImageForm3");

const image1 = document.getElementById('postImage_1');
const image2 = document.getElementById('postImage_2');
const image3 = document.getElementById('postImage_3');

const uploadBtn1 = document.getElementById('uploadBtn1');
const uploadBtn2 = document.getElementById('uploadBtn2');
const uploadBtn3 = document.getElementById('uploadBtn3');

let postId;


formPost_1.addEventListener("submit", e => {
    e.preventDefault();

    nextBtn.disabled = true;
    // formPost_2.classList.remove("d-none");
    formPost_2.classList.add('visible');
    
    const dataPost = {
        title: post_title.value,
        content: post_content.value
    }

    fetch("/posting", {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-type": "Application/json",
        },
        body: JSON.stringify(dataPost)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }  else throw Error(response.statusText);
        })
        .then(json => {
            postId = json.id;
        })
})


function fetchImage(image) {
    const formData = new FormData();
    if (image.files.length > 0) {
        formData.append('image', image.files[0]);
        fetch(`/post-images/${postId}`, {
            method: 'POST',
            headers:{
                "Authorization":`Bearer ${token}`,
            },
            body: formData
        }).then(response => {
            if (response.ok) {
                return response.json();
            }
            else throw Error(response.statusText);
        })
    } else {
        console.log("Didn't input image file");
    }
}

imageForm1.addEventListener('submit', e => {
    e.preventDefault();
    uploadBtn1.disabled = true;
    fetchImage(image1);
})
imageForm2.addEventListener('submit', e => {
    e.preventDefault();
    uploadBtn2.disabled = true;
    fetchImage(image2);
})
imageForm3.addEventListener('submit', e => {
    e.preventDefault();
    uploadBtn3.disabled = true;
    fetchImage(image3);
})

completeForm.addEventListener('submit', e => {
    e.preventDefault();
    location.href = `/travel/post/${postId}`;
})

function handleSlideUp() {
    nextBtn.disabled = true;
    // formPost_2.classList.remove("d-none");
    formPost_2.classList.add('visible');
}