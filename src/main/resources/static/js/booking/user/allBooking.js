const token = localStorage.getItem("token");
if (!token){
    location.href="/travel/login";
}

const allBooking = document.getElementById("allBooking");

function display(booking){
    const div = document.createElement("div");

    div.className= "d-flex flex-wrap border-bottom p-3 align-items-center";
    div.innerHTML=
    `
    <div class="col-5 col-lg-2 text-center">
        ${booking.packageTitle}
    </div>
    <div id="numPeople${booking.id}" class="d-none d-lg-block col-1 text-center">
        ${booking.numOfPeople}명
    </div>
    <div id="" class="d-none d-lg-block col-1 text-center ">
        ${booking.payment.toLocaleString()}₩
    </div>
    <div class="d-none d-lg-block col-2 text-center ">
        ${booking.departureDay}
    </div>
    <div id="payment${booking.id}" class="col-2 col-lg-1 text-center">
        
    </div>
    <div id="cancel${booking.id}" class="col-2 col-lg-1 text-center ">
        
    </div>
    <div id="rating${booking.id}" class="d-none d-lg-block col-2 text-center ">
        
    </div>
    <div id="status${booking.id}" class="col-3 col-lg-2 text-center ">
        
    </div>
    `;
    allBooking.appendChild(div);

    const paymentBook = document.getElementById(`payment${booking.id}`);
    const cancelBook = document.getElementById(`cancel${booking.id}`);
    const ratingBook = document.getElementById(`rating${booking.id}`);
    const statusBook = document.getElementById(`status${booking.id}`);
    if (booking.status==="Booking_successful"){
        paymentBook.innerHTML= `<a href="/travel/payment/${booking.id}" type="button" class="btn btn-outline-primary">결제</a>`;
        cancelBook.innerHTML= `<button id="cancelButton${booking.id}" class="btn btn-outline-danger">취소</button>`;
        ratingBook.innerHTML= `<span class="text-bg-secondary">불가</span>`;
        statusBook.innerHTML=`<span class="text-bg-info">예약 성공</span>`;
    } else if(booking.status==="Cancel_booking"){
        paymentBook.innerHTML=`<span class="text-bg-secondary">불가</span>`;
        cancelBook.innerHTML=`<span class="text-bg-secondary">불가</span>`;
        ratingBook.innerHTML=`<span class="text-bg-secondary">불가</span>`;
        statusBook.innerHTML=`<span class="text-bg-danger">예약 취소</span>`;
    } else if(booking.status==="Payment_successful"){
        paymentBook.innerHTML=`<span class="text-bg-successful">완료</span>`;
        cancelBook.innerHTML=`<span class="text-bg-secondary">불가</span>`;
        ratingBook.innerHTML=`<span class="text-bg-secondary">불가</span>`;
        statusBook.innerHTML = `<span class="text-bg-warning">결제 성공</span>`;
    } else{
        paymentBook.innerHTML=`<span class="text-bg-successful">완료</span>`;
        cancelBook.innerHTML=`<span class="text-bg-secondary">불가</span>`;
        if (booking.rating===null){
            ratingBook.innerHTML=`<button id="ratingButton${booking.id}" class="btn btn-outline-warning">평점</button>`;
        } else{
            ratingBook.innerHTML=
            `
            <span>
                <i id="1${booking.id}" class="fa-solid fa-star"></i>
                <i id="2${booking.id}" class="fa-solid fa-star"></i>
                <i id="3${booking.id}" class="fa-solid fa-star"></i>
                <i id="4${booking.id}" class="fa-solid fa-star"></i>
                <i id="5${booking.id}" class="fa-solid fa-star"></i>
            </span>
            `;
            for (let i=1; i<=booking.rating; i++){
                document.getElementById(`${i}${booking.id}`).classList.add("text-warning");
            }
        }
        statusBook.innerHTML =`<span class="text-bg-success">완료</span>`;
    }
}
function totalBooking(){
    fetch("/booking", {
        method: "GET",
        headers: {
            "Content-type": "Application/Json",
            "Authorization": `Bearer ${token}`,
        }
    }).then(response=>{
        if (response.ok){ return response.json()}
        else {return response.text().then(text=>{alert(text)})}
    }).then(json=>{
        allBooking.innerHTML=``;
        json.forEach(booking=>{
            display(booking);
            editBooking(booking);
        })
    })
}

function editBooking(booking){
    
    const cancelButton = document.getElementById(`cancelButton${booking.id}`);
    // const ratingButton = document.getElementById(`ratingButton${booking.id}`);
    
    
    if (cancelButton){
        cancelButton.addEventListener("click", e=>{
            e.preventDefault();
            const confirmCancel = confirm("Are you sure to cancel booking?");
            if (confirmCancel){
                fetch(`/booking/${booking.id}/cancel`, {
                    method: "PUT",
                    headers: {
                        "Content-type": "Application/json",
                        "Authorization": `Bearer ${token}`,
                    }
                }).then(response=>{
                    if (response.ok){
                        return response.json();
                    }
                    else {return response.text().then(text=>{alert(text)})}
                }).then(booking=>{
                    document.getElementById(`payment${booking.id}`).innerHTML=
                    `<span class="text-bg-secondary">불가</span>`;
                    document.getElementById(`cancel${booking.id}`).innerHTML=
                    `<span class="text-bg-secondary">불가</span>`;
                    document.getElementById(`rating${booking.id}`).innerHTML=
                    `<span class="text-bg-secondary">불가</span>`;
                    document.getElementById(`status${booking.id}`).innerHTML=
                    `<span class="text-bg-danger">예약 취소</span>`
                    })
            }
        })
    }
    function clickRating(){
        if (document.getElementById(`ratingButton${booking.id}`)){
            document.getElementById(`ratingButton${booking.id}`).addEventListener("click", e=>{
                e.preventDefault();
                const ratingArea = document.getElementById("ratingArea");
                ratingArea.classList.remove("d-none");
                document.getElementById("ratingArea-close").addEventListener("click", e=>{
                    e.preventDefault();
                    ratingArea.classList.add("d-none");
                })
                document.getElementById("button").innerHTML=
                `<button id="ratingbtn${booking.id}" class="btn btn-outline-primary">평가</button>`;
                document.getElementById("ratingInput").innerHTML=
                `
                <span id="ratingInput${booking.id}">
                    <a href="#" class="text-secondary"><i id="r1${booking.id}" class="fa-solid fa-star"></i></a>
                    <a href="#" class="text-secondary"><i id="r2${booking.id}" class="fa-solid fa-star"></i></a>
                    <a href="#" class="text-secondary"><i id="r3${booking.id}" class="fa-solid fa-star"></i></a>
                    <a href="#" class="text-secondary"><i id="r4${booking.id}" class="fa-solid fa-star"></i></a>
                    <a href="#" class="text-secondary"><i id="r5${booking.id}" class="fa-solid fa-star"></i></a>
                </span>
                `;
                
                let score;
                for (let i=1; i<=5; i++){
                    document.getElementById(`r${i}${booking.id}`).addEventListener("click", e=>{
                        e.preventDefault();
                        document.querySelectorAll(`#ratingInput${booking.id} i`).forEach(star=>{
                            star.classList.remove("text-warning");
                        })
                        for (let j=1; j<=i; j++){
                            document.getElementById(`r${j}${booking.id}`).classList.add("text-warning");
                        }
                        score=i;
                    })
                }
                document.getElementById(`ratingbtn${booking.id}`).addEventListener("click", e=>{
                    e.preventDefault();
                    ratingArea.classList.add("d-none");
                    fetch(`/booking/${booking.id}/rating`, {
                        method: "PUT", 
                        headers: {
                            "Content-type": "Application/json",
                            "Authorization": `Bearer ${token}`,
                        },
                        body: JSON.stringify({rating: score})
                    }).then(response=>{
                        if (response.ok){
                            return response.json();
                        }
                        else {return response.text().then(text=>{alert(text)})}
                    }).then(booking=>{
                        document.getElementById(`rating${booking.id}`).innerHTML=
                        `
                        <span>
                            <i id="1${booking.id}" class="fa-solid fa-star"></i>
                            <i id="2${booking.id}" class="fa-solid fa-star"></i>
                            <i id="3${booking.id}" class="fa-solid fa-star"></i>
                            <i id="4${booking.id}" class="fa-solid fa-star"></i>
                            <i id="5${booking.id}" class="fa-solid fa-star"></i>
                        </span>
                        `;
                        for (let i=1; i<=booking.rating; i++){
                            document.getElementById(`${i}${booking.id}`).classList.add("text-warning");
                        }
                    })
                })
            })    
        }
    }
    clickRating();
}

totalBooking();
document.getElementById("searchByPackage").addEventListener("submit", e=>{
    e.preventDefault();
    const packSearchId = document.getElementById("packIdSearch");
    if (packSearchId.value==="") return totalBooking();

    fetch(`/booking/search?packageId=${packSearchId.value}`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        }
    }).then(response=>{
        if (response.ok){ return response.json()}
        else {return response.text().then(text=>{alert(text)})}
    }).then(json=>{
        allBooking.innerHTML=``;
        json.forEach(booking=>{
            display(booking);
            editBooking(booking);
        })
    })
})
document.getElementById("searchByStatus").addEventListener("submit", e=>{
    e.preventDefault();
    const statusSearch = document.getElementById("statusSearch");
    if (statusSearch.value==="") return totalBooking();
    fetch(`/booking/search?status=${statusSearch.value}`,{
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        }
    }).then(response=>{
        if (response.ok){ return response.json()}
        else {return response.text().then(text=>{alert(text)})}
    }).then(json=>{
        allBooking.innerHTML=``;
        json.forEach(booking=>{
            display(booking);
            editBooking(booking);
        })
    })

})