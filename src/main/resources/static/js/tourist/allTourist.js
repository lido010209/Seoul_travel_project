const city = document.getElementById("city");
const touristArea = document.getElementById("tourist");
const modalAll = document.getElementById("modal");

fetch("/tours/kor/city", {
    method: "GET",
}).then(response=>{
    if (response.ok){
        return response.json();
    } else{
        return response.text().then(text=>{
            alert(text);
        })
    }
}).then(json=>{
    json.forEach(item=>{
        cityDisplay(item);
    })
})

function cityDisplay(item){
    const optionDis= document.createElement("option");
    optionDis.value= item.code;
    optionDis.innerText=item.name;
    optionDis.id=`code${item.code}`;
    city.appendChild(optionDis);
}

function touristDisplay(tourist){
    let touristImg;
    if (tourist.firstimage=="") touristImg=`/static/visual/tourist/seoul${Math.floor(Math.random() * 10) + 1}.png`;
    else {touristImg=tourist.firstimage;}
    const divTour = document.createElement("div");
    divTour.className= "col-12 col-md-6 p-3";
    divTour.id = `tour${tourist.contentid}`;
    divTour.innerHTML= 
    `
    <a href="#" class="text-decoration-none text-dark">
        <div class="p-2 bg-warning-subtle rounded">
            <img src="${touristImg}" class="w-100 image img-fluid rounded">
            <div class="text-center p-3">
                <h4 class="fw-bold">${tourist.title}</h4>
                <p class="text-center">${tourist.addr1}</p>
            </div>
        </div>
    </a>
    `;
    touristArea.appendChild(divTour);
    document.getElementById(`tour${tourist.contentid}`).addEventListener("click", e=>{
        e.preventDefault();
        modalAll.classList.remove("d-none");
        mainDis(tourist.contentid);
        const scrollCon = document.getElementById("scroll-control");
        scrollCon.scrollTop = 0;
    })
    document.getElementById("close-modal").addEventListener("click", e=>{
        e.preventDefault();
        modalAll.classList.add("d-none");
    })
}

// Pagination
const pagination = document.getElementById("pagination");
const previous = document.getElementById("previous");
const next = document.getElementById("next");
const searchByCity = document.getElementById("search-by-city");
// const searchKw = document.getElementById("search-keyword");
document.getElementById(`code${city.value}`).selected=true;

let currentPage =1;
let currentRange =1;
function totalPage(result){
    pagination.innerHTML=``;
    const numOfPage = Math.ceil(result.totalCount/10);
    const pageShow = 9;
    const totalRange = Math.ceil(numOfPage/pageShow);
    console.log(totalRange);
    
    // const currentPage = result.pageNo;
    currentRange = Math.ceil(currentPage/pageShow);
    console.log(currentRange);
    if (currentRange===totalRange){next.classList.add("disabled");} 
    else {
        next.addEventListener("click", e=>{
            e.preventDefault();
            currentRange++;
                listPage();
                currentPage = (currentRange-1)*9+1;
                document.getElementById(`page${currentPage}`).classList.add("active");
                defaultDis(currentPage);
                previous.classList.remove("disabled");
            
            // else if (currentRange===totalRange) next.classList.add("disabled");
        })
    }
    
    if (currentRange===1) {previous.classList.add("disabled");}
    else {
        previous.addEventListener("click", e=>{
            e.preventDefault();
            currentRange--;
                // location.href=`/view/tour/${(currentRange-1)*9+10}`
                listPage();
                currentPage = (currentRange-1)*9+9;
                document.getElementById(`page${currentPage}`).classList.add("active");
                defaultDis(currentPage);
                next.classList.remove("disabled");
            
            // else if (currentRange===1) previous.classList.add("disabled");
        })
    }
    
    listPage();
    document.getElementById(`page${currentPage}`).classList.add("active");
    function listPage(){
        pagination.innerHTML=``;
        let endRange = Math.min((currentRange-1)*9+10, numOfPage+1);

        for (let i = (currentRange-1)*9+1; i<endRange; i++){
            const li = document.createElement("li");
            li.className="page-item";
            li.id=`page${i}`;
            li.innerHTML=`<a class="page-link" href="#">${i}</a>`;
            pagination.appendChild(li);
            document.getElementById(`page${i}`).addEventListener("click", e=>{
                e.preventDefault();
                document.getElementById(`page${currentPage}`).classList.remove("active");
                document.getElementById(`page${i}`).classList.add("active");
                currentPage=i;
                defaultDis(currentPage);
            })
        }  
    }
}

function defaultDis(pageNo){
    fetch(`/tours/kor/landmark?pageNo=${pageNo}&sigunguCode=${city.value}`,{
        method: "GET",
    }).then(response=>{
        if (response.ok){
            return response.json();
        } else {
            return response.text().then(text=>{alert(text)});
        }
    }).then(json=>{
        touristArea.innerHTML=``;
        json.items.item.forEach(tourist=>{
            touristDisplay(tourist);
        })
        totalPage(json);
    })
}
defaultDis(currentPage);


searchByCity.addEventListener("submit", e=>{
    e.preventDefault();
    document.getElementById(`code${city.value}`).selected=true;
    currentPage=1;
    defaultDis(currentPage);
})


