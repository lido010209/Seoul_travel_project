const token = localStorage.getItem("token");

const formBooking = document.getElementById("form-booking");
const packageId = document.getElementById("package");
const departDay = document.getElementById("departDay");
const numOfPeople = document.getElementById("numOfPeople");
const confirmArea = document.getElementById("confirm-area");
const noConfirm = document.getElementById("no-confirm");


formBooking.addEventListener("submit", e=>{
    e.preventDefault();
    if (!localStorage.getItem("token")){
        location.href = "/travel/login";
        return;
    }
    confirmArea.classList.remove("d-none");
    noConfirm.addEventListener("click", e=>{
        e.preventDefault();
        confirmArea.classList.add("d-none");
    })
    const packageConfirm = document.getElementById("package-confirm");
    const schedule = document.getElementById("schedule");
    const people = document.getElementById("people");

    let packageTitle;
    let packagePrice;
    console.log(packageId.value);
    if (packageId.value==="1"){
        packageTitle= "서울의 과거와 현재를 걷다: 전통과 현대의 1박 2일 여행 ~ 300,000₩/인";
        packagePrice = 300000;
    } else if (packageId.value==="2"){
        packageTitle= "서울의 예술과 전통을 담은 1박 2일 도심 여행 ~ 300,000₩/인";
        packagePrice = 300000;
    } else {
        packageTitle= "서울 전통과 현대를 잇는 1박 2일 여행 코스 ~ 500,000₩/인";
        packagePrice = 500000;
    }

    packageConfirm.innerHTML=
    `
    <a href="/travel/package/${packageId.value}" target="_blank" class="text-decoration-none">
        코스${packageId.value}: ${packageTitle}
    </a>
    `;
    const startDate = new Date(departDay.value);
    const styear = startDate.getFullYear();
    const stmonth = String(startDate.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const stday = String(startDate.getDate()).padStart(2, '0');

    const finishDate = new Date(departDay.value);
    finishDate.setDate(finishDate.getDate() + 3);
    const fiyear = finishDate.getFullYear();
    const fimonth = String(finishDate.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const fiday = String(finishDate.getDate()).padStart(2, '0');

    schedule.innerText= `${styear}/${stmonth}/${stday}~${fiyear}/${fimonth}/${fiday}`;
    people.innerText=numOfPeople.value;
    const totalPayment= document.getElementById("total-payment");

    let payAmount = packagePrice*numOfPeople.value;
    
    if (numOfPeople.value<5) {
        totalPayment.innerHTML=
        `
        <label class="form-label">요금</label><br>
        <div class="w-100 border p-2 rounded">
            ${payAmount.toLocaleString()}₩
        </div>
        `;

    } else if (numOfPeople.value<10) {
        totalPayment.innerHTML=
        `
        <label class="form-label">요금 <span id="coupon" class="fw-bold">(쿠폰: 20%)</span> </label><br>
        <div class="w-100 border p-2 rounded">
            <s>${payAmount.toLocaleString()}₩</s>=>${(payAmount*0.8).toLocaleString()}₩
        </div>
        `;

    } else if (numOfPeople.value<20) {
        totalPayment.innerHTML=
        `
        <label class="form-label">요금 <span id="coupon" class="fw-bold">(쿠폰: 30%)</span> </label><br>
        <div class="w-100 border p-2 rounded">
            <s>${payAmount.toLocaleString()}₩</s>=>${(payAmount*0.7).toLocaleString()}₩
        </div>
        `;
    } else {
        totalPayment.innerHTML=
        `
        <label class="form-label">요금 <span id="coupon" class="fw-bold">(쿠폰: 50%)</span> </label><br>
        <div class="w-100 border p-2 rounded">
            <s>${payAmount.toLocaleString()}₩</s>=>${(payAmount*0.5).toLocaleString()}₩
        </div>
        `;
    }

    document.getElementById("form-confirm").addEventListener("click", e=>{
        e.preventDefault();
        const inputData = {
            packageId: packageId.value,
            numOfPeople: numOfPeople.value,
            departureDay: departDay.value,
        }
        fetch("/booking", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-type": "Application/json",
            },
            body: JSON.stringify(inputData),
        }).then(response=>{
            if (response.ok){
                location.href = "/travel/my-booking";
                return response.json();
            }
            else {return response.text().then(text=>{alert(text)});}
        })
    })
})