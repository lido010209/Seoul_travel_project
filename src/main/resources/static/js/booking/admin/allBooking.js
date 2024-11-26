const token = localStorage.getItem("token");
const allBooking = document.getElementById("allBooking");
const authorities = localStorage.getItem("authorities");

if (!(authorities && authorities.includes("ROLE_ADMIN"))){
    document.getElementById("all-display").innerHTML=``;
    alert("접속 못 합니다!!!")
    location.href="/travel/home";
}

function display(booking){
    const div = document.createElement("div");

    div.className= "d-flex flex-wrap border-bottom p-3 align-items-center";
    div.innerHTML=
    `
    <div class="col-5 col-lg-2 text-center ">
        ${booking.packageTitle}         
    </div>
    <div class="d-none d-lg-block col-1 text-center ">
        ${booking.numOfPeople}명
    </div>
    <div class="d-none d-lg-block col-1 text-center ">
        ${booking.payment.toLocaleString()}₩
    </div>
    <div class="d-none d-lg-block col-2 text-center ">
        ${booking.departureDay}
    </div>
    <div class="col-2 text-center ">
        ${booking.customerPhone}
    </div>
    <div class="col-2 col-lg-1 text-center ">
        ${booking.customerName}
    </div>
    <div id="rating${booking.id}" class="d-none d-lg-block col-1 text-center ">
        <span class="text-bg-secondary">보기</span>
    </div>
    <div id="status${booking.id}" class="col-3 col-lg-2 text-center ">
        <span class="text-bg-info">미결제</span>
    </div>    
    `;
    allBooking.appendChild(div);

    const ratingBook = document.getElementById(`rating${booking.id}`);
    const statusBook = document.getElementById(`status${booking.id}`);
    if (booking.rating===null){
        ratingBook.innerHTML=`<span class="text-bg-secondary">보기</span>`;
    } else {ratingBook.innerHTML=`<span class="text-bg-warning">${booking.rating}/5</span>`;}

    if (booking.status==="Booking_successful"){
        statusBook.innerHTML=`<span class="text-bg-info">미결제</span>`;
    } else if(booking.status==="Cancel_booking"){
        statusBook.innerHTML=`<span class="text-bg-danger">예약 취소</span>`;
    } else if(booking.status==="Payment_successful"){
        statusBook.innerHTML = `<button id="confirmBtn${booking.id}" class="btn btn-outline-primary">확인</button>`;
    } else{
        statusBook.innerHTML =`<span class="text-bg-success">완료</span>`;
    }
}

function editBooking(booking){
    const confirmBtn= document.getElementById(`confirmBtn${booking.id}`)
    if (confirmBtn){
        confirmBtn.addEventListener("click", e=>{
            e.preventDefault();
            fetch(`/manage-booking/${booking.id}`, {
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
                document.getElementById(`status${booking.id}`).innerHTML =`<span class="text-bg-success">완료</span>`;
            })
        })
    }
}

fetch("/manage-booking", {
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

document.getElementById("searchByPackage").addEventListener("submit", e=>{
    e.preventDefault();
    const packSearchId = document.getElementById("packIdSearch");
    fetch(`/manage-booking/search?packageId=${packSearchId.value}`,{
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
    fetch(`/manage-booking/search?status=${statusSearch.value}`,{
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