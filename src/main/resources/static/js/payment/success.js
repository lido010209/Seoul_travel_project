const token= localStorage.getItem("token");
if (!token){
  location.href=`/travel/login`;
}
const pathSplit = location.pathname.split("/");
const bookId = pathSplit[pathSplit.length-1][0];

// 쿼리 파라미터 값을 서버로 전달해 결제 요청할 때 보낸 데이터와 동일한지 반드시 확인하세요.
  // 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
  const urlParams = new URLSearchParams(window.location.search);

  // 서버로 결제 승인에 필요한 결제 정보를 보내세요.
  async function confirm() {
    const requestData = {
      paymentKey: urlParams.get("paymentKey"),
      orderId: urlParams.get("orderId"),
      amount: urlParams.get("amount"),
    };

    const response = await fetch("/confirm/widget", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
      body: JSON.stringify(requestData),
    });

    const json = await response.json();

    if (!response.ok) {
      throw { message: json.message, code: json.code };
    }

    document.getElementById("requestedAt").innerText = json.requestedAt;
    return json;
  }

  confirm();

//   const paymentKeyElement = document.getElementById("paymentKey");
  const orderIdElement = document.getElementById("orderId");
  const amountElement = document.getElementById("amount");

  orderIdElement.textContent = urlParams.get("orderId");
  amountElement.textContent = Number(urlParams.get("amount")).toLocaleString() + "원";
//   paymentKeyElement.textContent = urlParams.get("paymentKey");

fetch(`/booking/${bookId}`, {
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
})