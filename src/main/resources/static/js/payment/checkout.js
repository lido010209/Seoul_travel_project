const token = localStorage.getItem("token");
if (!token){
    location.href="/travel/login";
}

async function main(booking) {
    const button = document.getElementById("payment-button");
    // const coupon = document.getElementById("coupon-box");
    const amount = {
      currency: "KRW",
      value: booking.payment,
    };
    // ------  결제위젯 초기화 ------
    // TODO: clientKey는 개발자센터의 결제위젯 연동 키 > 클라이언트 키로 바꾸세요.
    // TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요. 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
    // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
    const clientKey = "<clientKey>";
    // const customerKey = generateRandomString();
    const tossPayments = TossPayments(clientKey);
    // 회원 결제
    // @docs https://docs.tosspayments.com/sdk/v2/js#tosspaymentswidgets
    // const widgets = tossPayments.widgets({
    //   customerKey,
    // });
    // 비회원 결제
    const widgets = tossPayments.widgets({customerKey: TossPayments.ANONYMOUS});

    // ------  주문서의 결제 금액 설정 ------
    // TODO: 위젯의 결제금액을 결제하려는 금액으로 초기화하세요.
    // TODO: renderPaymentMethods, renderAgreement, requestPayment 보다 반드시 선행되어야 합니다.
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount
    await widgets.setAmount(amount);

    await Promise.all([
      // ------  결제 UI 렌더링 ------
      // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
      widgets.renderPaymentMethods({
        selector: "#payment-method",
        // 렌더링하고 싶은 결제 UI의 variantKey
        // 결제 수단 및 스타일이 다른 멀티 UI를 직접 만들고 싶다면 계약이 필요해요.
        // @docs https://docs.tosspayments.com/guides/v2/payment-widget/admin#새로운-결제-ui-추가하기
        variantKey: "DEFAULT",
      }),
      // ------  이용약관 UI 렌더링 ------
      // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderagreement
      widgets.renderAgreement({
        selector: "#agreement",
        variantKey: "AGREEMENT",
      }),
    ]);

    // ------  주문서의 결제 금액이 변경되었을 경우 결제 금액 업데이트 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount
    

    // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
    button.addEventListener("click", async function () {
      // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
      // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
      await widgets.requestPayment({
        orderId: generateRandomString(),
        orderName: `${booking.packageTitle}`,
        successUrl:window.location.origin + `/travel/payment/success/${bookId}`,
        failUrl: window.location.origin + `/travel/payment/fail/${bookId}`,
      });
    });
  }
function generateRandomString() {
    return window.btoa(Math.random()).slice(0, 20);
}

// Detail booking

const pathSplit = location.pathname.split("/");
const bookId = pathSplit[pathSplit.length-1];

function detailBooking(booking){
    const packageConfirm = document.getElementById("package-confirm");
    const schedule = document.getElementById("schedule");
    const people = document.getElementById("people");

    packageConfirm.innerHTML=
    `
    <a href="/travel/package/${booking.packageId}" target="_blank" class="text-decoration-none">
        코스${booking.packageId}: ${booking.packageTitle}
    </a>
    `;
    const startDate = new Date(booking.departureDay);
    const styear = startDate.getFullYear();
    const stmonth = String(startDate.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const stday = String(startDate.getDate()).padStart(2, '0');

    const finishDate = new Date(booking.departureDay);
    finishDate.setDate(finishDate.getDate() + 3);
    const fiyear = finishDate.getFullYear();
    const fimonth = String(finishDate.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const fiday = String(finishDate.getDate()).padStart(2, '0');

    schedule.innerText= `${styear}/${stmonth}/${stday}~${fiyear}/${fimonth}/${fiday}`;
    people.innerText=booking.numOfPeople;
    
    const coupon = document.getElementById("coupon");
    if (booking.coupon===0) coupon.classList.add("d-none");
    else coupon.innerText= `쿠폰: ${booking.coupon}%`;

    document.getElementById("totalPay").innerText= `${booking.payment.toLocaleString()}₩`;
    document.getElementById("packImage").src=`/static/visual/package/pack${booking.packageId}.png`;


}

fetch(`/booking/${bookId}`, {
    headers: {
        "Content-type": "Application/json",
        "Authorization": `Bearer ${token}`,
    }
}).then(response=>{
    if(response.ok){
        return response.json();
    } else{
        document.getElementById("body").innerHTML=``;
        response.text().then(text=>{alert(text);});
        location.href="/travel/my-booking";
    }
}).then(booking=>{
    if(booking.status!=="Booking_successful"){
        document.getElementById("body").innerHTML=``;
        alert("결제 불가능합니다!!!");
        location.href="/travel/my-booking";
    }
    fetch("/users", {
        headers: {
            "Content-type": "Application/json",
            "Authorization": `Bearer ${token}`,
        }
    }).then(response=>{
        if(response.ok){
            return response.json();
        } else{
            return response.text().then(text=>{alert(text);})
        }
    }).then(user=>{
        if (user.id!==booking.customerId){
            document.getElementById("body").innerHTML=``;
            alert("이 예약 존재하지 않습니다!");
            location.href="/travel/my-booking";
        }
    })
    detailBooking(booking);
    main(booking);
    
})



