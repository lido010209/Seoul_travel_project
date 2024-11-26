const pathSplit = location.pathname.split("/");
const packageId = pathSplit[pathSplit.length-1];

const prevPage = document.getElementById("prevPage");
const nextPage = document.getElementById("nextPage");
if (packageId==="1") {
    prevPage.classList.add("text-secondary");
} else {prevPage.href=`/travel/package/${Number(packageId)-1}`}

if (packageId==="3"){
    nextPage.classList.add("text-secondary");
} else {nextPage.href=`/travel/package/${Number(packageId)+1}`}

const averRating = document.getElementById("aver-rating");
const numOfRating = document.getElementById("numOfRating");
const prev = document.getElementById("prev");
const next = document.getElementById("next");

const packTitle = document.getElementById("package-title");
const mainday1= document.getElementById("main-day1");
const mainday2= document.getElementById("main-day2");

const originPrice = document.getElementById("origin-price");
const currentPrice = document.getElementById("current-price");

const detailday1 = document.getElementById("detail-day1");
const detailday2 = document.getElementById("detail-day2");
const packageIdDis = document.getElementById("packageId")
const imgCard = document.getElementById("imgCard");

function displayImg(contentid, img){
    fetch(`/tours/kor/detail/${contentid}`, {
        method: "GET",
    }).then(response=>{
        if (response.ok){return response.json()}
        else{
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(result=>{
        let imgSrc;
        if (result.firstimage===""){
            imgSrc=img;
        } else {imgSrc=result.firstimage;}
        // const divDis = document.createElement("div");
        // divDis.className= "carousel-item";
        // divDis.id = `landmark${contentid}`;
        document.getElementById(`landmark${contentid}`).innerHTML= `<img src="${imgSrc}" class="d-block w-100 rounded" style="height: 430px;">`;
        // imgCard.appendChild(divDis);
    }).catch(e=>{
        e.message;
    })
}

let allLandmarks;
fetch(`/packages/${packageId}`, {
    method:"GET",
}).then(response=>{
    if (response.ok){
        return response.json();
    } else { return response.text()}
}).then(package=>{
    const allLandmarks = package.landmarks;
    for (let i=0; i<allLandmarks.length; i++){
        const divDis = document.createElement("div");
        divDis.className= "carousel-item";
        divDis.id = `landmark${allLandmarks[i]}`;
        imgCard.appendChild(divDis);
        displayImg(allLandmarks[i], `/static/visual/tourist/seoul${i}.png`)
    }

    let total = allLandmarks.length;
        let currentImage=0;
        const landmarkDis = document.getElementById(`landmark${allLandmarks[currentImage]}`);
        landmarkDis.classList.add("active");
        prev.addEventListener("click", e=>{
            e.preventDefault();
            document.getElementById(`landmark${allLandmarks[currentImage]}`).classList.remove("active");
            if (currentImage===0) {
                currentImage=total-1;
            } else{currentImage--;}
            document.getElementById(`landmark${allLandmarks[currentImage]}`).classList.add("active");
        })
        next.addEventListener("click", e=>{
            e.preventDefault();
            document.getElementById(`landmark${allLandmarks[currentImage]}`).classList.remove("active");
            if (currentImage===total-1) {
                currentImage=0;
            } else{currentImage++;}
            document.getElementById(`landmark${allLandmarks[currentImage]}`).classList.add("active");
        })

    packTitle.innerText = package.title;
    mainday1.innerHTML=package.mainActivities[0].replace(/\n/g,"<br>");
    mainday2.innerHTML=package.mainActivities[1].replace(/\n/g,"<br>");
    originPrice.innerText= `${package.price*1.6/1000},000₩`;
    currentPrice.innerText= `${package.price/1000},000₩`;
    detailday1.innerHTML = package.details[0].replace(/\n/g, "<br>");
    detailday2.innerHTML = package.details[1].replace(/\n/g, "<br>");
    
    numOfRating.innerText= `${package.numOfRating}개 이용 후기`;
    if (package.numOfRating>0){
        averRating.innerText=`${package.avgRating}/5`;
        averRating.style=`width: ${package.avgRating/5*100}%`;
    } else {
        averRating.innerText=``;
        averRating.style=`width: 0%`;
    }
    packageIdDis.innerText= package.id;
})


    