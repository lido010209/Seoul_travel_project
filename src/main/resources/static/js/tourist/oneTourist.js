const modal = document.getElementById("modal");
const closeModal = document.getElementById("close-modal");
const imageModal = document.getElementById("image-modal");
const mapContainer = document.getElementById("map");
const tourName = document.getElementById("tour-name")
const tourAddr = document.getElementById("tour-address");
const overview = document.getElementById("overview");
let mapX;
let mapY;
let mapDis;

function disOneTour(result){
    if (result.firstimage==="") {imageModal.src=`/static/visual/tourist/seoul${Math.floor(Math.random() * 10) + 1}.png`;}
    else {imageModal.src=result.firstimage};

    tourName.innerText= result.title;
    tourAddr.innerText=result.addr1;
    overview.innerHTML = result.overview.replace(/\n/g, "<br>");
    mapX = result.mapx;
    mapY = result.mapy;
    mapDis = new kakao.maps.Map(mapContainer, {
        center: new kakao.maps.LatLng(mapY, mapX),
        level:4,
    })
    const marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(mapY, mapX)
    })
    marker.setMap(mapDis);
    const innerContent = 
    `
    <div class="d-flex align-items-center p-2">
      <div class="me-3 col-4">
        <img src="${imageModal.src}" class="fluid-img" style="height: 70px;">
      </div>
      <div class="col-8 pe-2">
        <strong>${result.title}</strong><br>
        <i class="fa-solid fa-location-dot"></i> ${result.addr1}
      </div>
    </div>
    `;

    const infoWindow = new kakao.maps.InfoWindow({
      content: innerContent,
    })

    kakao.maps.event.addListener(marker, "mouseover", function(){
      infoWindow.open(mapDis, marker);
    })
    kakao.maps.event.addListener(marker, "mouseout", function(){
      infoWindow.close();
    })
}

function displayMapCon(mapX, mapY, map, imageSrc, srcContent, title, addr){
    const positionPoint = new kakao.maps.LatLng(mapY, mapX);
    const imageSize = new kakao.maps.Size(30,40);
    const markerImg = new kakao.maps.MarkerImage(imageSrc, imageSize);
    const marker = new kakao.maps.Marker({
        position: positionPoint,
        image: markerImg,
    })
    marker.setMap(map);

    const innerContent = 
    `
    <div class="d-flex align-items-center p-2">
      <div class="me-3 col-4">
        <img src="${srcContent}" class="fluid-img" style="height: 70px;">
      </div>
      <div class="col-8 pe-2">
        <strong>${title}</strong><br>
        <i class="fa-solid fa-location-dot"></i> ${addr}
      </div>
    </div>
    `;
    const infoWindow = new kakao.maps.InfoWindow({
        content: innerContent,
      })
  
      kakao.maps.event.addListener(marker, "mouseover", function(){
        infoWindow.open(map, marker);
      })
      kakao.maps.event.addListener(marker, "mouseout", function(){
        infoWindow.close();
      })
}
function restaurantMarker(result){
    let imgContent;
    if (result.firstimage===""){imgContent="/static/visual/around/res.png"}
    else {imgContent=result.firstimage}
    displayMapCon(result.mapx, result.mapy, mapDis, "/static/visual/marker/res.png",
        imgContent, result.title, result.addr1
    )
}
function shoppingMarker(result){
    let imgContent;
    if (result.firstimage===""){imgContent="/static/visual/around/shopping.png"}
    else {imgContent=result.firstimage}
    displayMapCon(result.mapx, result.mapy, mapDis, "/static/visual/marker/shopping.png",
        imgContent, result.title, result.addr1
    )
}

function disResAround(){
    const paramRes = {
        mapX: mapX,
        mapY: mapY,
        contentTypeId: 39,
    }
    fetch(`/tours/kor/around?` + new URLSearchParams(paramRes).toString(), {
        method: "GET"
    }).then(response=>{
        if (response.ok){return response.json()}
        else{
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(json=>{
        if (json.items){
            json.items.item.forEach(result=>{
                restaurantMarker(result);
            })
        }
    })
}
function disShopAround(){
    const paramShop = {
        mapX: mapX,
        mapY: mapY,
        contentTypeId: 38,
    }
    fetch(`/tours/kor/around?` + new URLSearchParams(paramShop).toString(), {
        method: "GET"
    }).then(response=>{
        if (response.ok){return response.json()}
        else{
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(json=>{
        if (json.items){
            json.items.item.forEach(result=>{
                shoppingMarker(result);
            })
        }
    })
}

function mainDis(contentId){
    fetch(`/tours/kor/detail/${contentId}`, {
        method: "GET"
    }).then(response=>{
        if(response.ok){return response.json();}
        else{
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(result=>{
        disOneTour(result);
        disResAround();
        disShopAround();
    })
}




