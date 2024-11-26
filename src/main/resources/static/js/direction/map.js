// result search


const searchDeparture = document.getElementById("search-departure");
const inputDeparture = document.getElementById("input-departure");
const searchArrival = document.getElementById("search-arrival");
const inputArrival = document.getElementById("input-arrival");
const researchResult = document.getElementById("research-result");
const search = document.getElementById("search");
const mapContainer = document.getElementById("map"); // 지도를 표시할 영역의 DOM 요소
const imageDep = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png";
const imageArr = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png";
const defMap = new kakao.maps.Map(mapContainer, {
    center: new kakao.maps.LatLng(37.5663, 126.9772),
    level: 3
});
let map =null;
let departure = null;
let arrival = null;
let bounds = new kakao.maps.LatLngBounds();
searchDeparture.addEventListener("submit", e=>{
    e.preventDefault();
    researchResult.innerHTML=``;
    search.classList.remove("d-none");
    directionDisplay.classList.add("d-none");
    const keyword = inputDeparture.value;
    fetch(`/n-search-location?keyword=${keyword}`, {
        method: "GET",
    }).then(response =>{
        if (response.ok){
            return response.json();
        } else{
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(places=>{
        const mapSearch = new kakao.maps.Map(mapContainer, {
            center: new kakao.maps.LatLng(37.5663, 126.9772),
            level: 3
        });
        const searchBounds = new kakao.maps.LatLngBounds();
        for (let i = 0; i < places.length; i++) {
            displayMarker(places, mapSearch, i);
            searchBounds.extend(new kakao.maps.LatLng(places[i].mapy, places[i].mapx));
        }
        mapSearch.setBounds(searchBounds);
        for (let i = 0; i < places.length; i++){
            const clickDeparture = document.getElementById(`${i}`);
            clickDeparture.addEventListener("click", e=>{
                e.preventDefault();
                departure = places[i];
                inputDeparture.value = places[i].title.replace(/<[^>]+>/g, '');
                researchResult.innerHTML=``;
                search.classList.add("d-none");
                if (arrival){
                    const dep = new kakao.maps.LatLng(places[i].mapy, places[i].mapx);
                    const arr = new kakao.maps.LatLng(arrival.mapy, arrival.mapx);
                    const mapOption = {
                        center: new kakao.maps.LatLng(37.5663, 126.9772),
                        level: 3
                    }
                    map = new kakao.maps.Map(mapContainer, mapOption);
                    bounds.extend(dep); bounds.extend(arr);
                    map.setBounds(bounds);
                    bounds = new kakao.maps.LatLngBounds();
                    displayFlag(places[i], map, imageDep);
                    displayFlag(arrival, map, imageArr);
                } else {
                    const mapOption = {
                        center: new kakao.maps.LatLng(places[i].mapy, places[i].mapx),
                        level: 1
                    }
                    map = new kakao.maps.Map(mapContainer, mapOption);
                    displayFlag(places[i], map, imageDep);
                }
            })
        }
    })
})

searchArrival.addEventListener("submit", e=>{
    e.preventDefault();
    researchResult.innerHTML=``;
    search.classList.remove("d-none");
    directionDisplay.classList.add("d-none");
    const keyword = inputArrival.value;
    fetch(`/n-search-location?keyword=${keyword}`, {
        method: "GET",
    }).then(response =>{
        if (response.ok){
            return response.json();
        } else{
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(places=>{
        const mapSearch = new kakao.maps.Map(mapContainer, {
            center: new kakao.maps.LatLng(37.5663, 126.9772),
            level: 3
        });
        const searchBounds = new kakao.maps.LatLngBounds();
        for (let i = 0; i < places.length; i++) {
            displayMarker(places, mapSearch, i);
            searchBounds.extend(new kakao.maps.LatLng(places[i].mapy, places[i].mapx));
        }
        mapSearch.setBounds(searchBounds);
        for (let i = 0; i < places.length; i++){
            const clickArrival = document.getElementById(`${i}`);
            clickArrival.addEventListener("click", e=>{
                e.preventDefault();
                arrival = places[i];
                inputArrival.value = places[i].title.replace(/<[^>]+>/g, '');
                researchResult.innerHTML=``;
                search.classList.add("d-none");
                if (departure){
                    const dep = new kakao.maps.LatLng(departure.mapy, departure.mapx);
                    const arr = new kakao.maps.LatLng(places[i].mapy, places[i].mapx);
                    const mapOption = {
                        center: new kakao.maps.LatLng(37.5663, 126.9772),
                        level: 3
                    }
                    map = new kakao.maps.Map(mapContainer, mapOption);
                    bounds.extend(dep); bounds.extend(arr);
                    map.setBounds(bounds);
                    bounds = new kakao.maps.LatLngBounds();
                    displayFlag(places[i], map, imageArr);
                    displayFlag(departure, map, imageDep);
                } else {
                    const mapOption = {
                        center: new kakao.maps.LatLng(places[i].mapy, places[i].mapx),
                        level: 1
                    }
                    map = new kakao.maps.Map(mapContainer, mapOption);
                    displayFlag(places[i], map, imageArr);
                }
            })
        }
    })
})

function displayMarker(places, map, idx){
    const latitude = places[idx].mapy; // 장소의 위도
    const longitude = places[idx].mapx;

    const imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    const imageSize = new kakao.maps.Size(24, 35); 
    const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    const marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도 객체 설정
        position: new kakao.maps.LatLng(latitude, longitude), // 마커의 위치 설정
        image: markerImage // 마커에 사용될 이미지 설정
    });
    marker.setMap(map);
    
    const iwContent = 
    `
    <div style="padding:5px;">
        <strong>${places[idx].title}</strong><br>
        ${places[idx].address}
    </div>
    `;
    const infowindow = new kakao.maps.InfoWindow({
        content : iwContent
    });

    kakao.maps.event.addListener(marker, 'mouseover', function() {
        infowindow.open(map, marker);
    });
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });

    const createA = document.createElement("a");
    createA.id = `${idx}`;
    createA.className = "border rounded w-100 p-3 text-dark text-decoration-none mb-2";
    createA.role="button";
    createA.innerHTML=`
    <strong>${places[idx].title}</strong><br>
    ${places[idx].address}
    `;
    researchResult.appendChild(createA);
}

function displayFlag(place, mapDisplay, imageFlag){
    const latitude = place.mapy; // 장소의 위도
    const longitude = place.mapx;
    
    const imageSize = new kakao.maps.Size(24, 35); 
    const markerImage = new kakao.maps.MarkerImage(imageFlag, imageSize);

    const marker = new kakao.maps.Marker({
        map: mapDisplay, // 마커를 표시할 지도 객체 설정
        position: new kakao.maps.LatLng(latitude, longitude), // 마커의 위치 설정
        image: markerImage // 마커에 사용될 이미지 설정
    });
    marker.setMap(mapDisplay);

    const iwContent = 
    `
    <div style="padding:5px;">
        <strong>${place.title}</strong><br>
        ${place.address}
    </div>
    `;
    const infowindow = new kakao.maps.InfoWindow({
        content : iwContent
    });

    kakao.maps.event.addListener(marker, 'mouseover', function() {
        infowindow.open(mapDisplay, marker);
    });
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });
}

const direction = document.getElementById("direction");
const directionDisplay = document.getElementById("direction-display");
let paramTran;
direction.addEventListener("click", e => {
    e.preventDefault();
    directionDisplay.classList.remove("d-none");
    search.classList.add("d-none");
    paramTran = {
        "sx": departure.mapx,
        "sy": departure.mapy,
        "ex": arrival.mapx,
        "ey": arrival.mapy,
    }
    console.log("departure X", departure.mapx);
    fetch(`/odsay/directions?${new URLSearchParams(paramTran).toString()}`, {
        method: "GET",
    }).then(response=>{
        if (response.ok){
            return response.json();
        } else {
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(result=>{
        console.log("searchType", result.searchType);
        transport(result);
        // const mapOption = {
        //     center: new kakao.maps.LatLng(37.5663, 126.9772),
        //     level: 3
        // }
        // map = new kakao.maps.Map(mapContainer, mapOption);
        // linePathBound.forEach(point=>{
        //     bounds.extend(point);
        // })
        // map.setBounds(bounds);
        // bounds = new kakao.maps.LatLngBounds();
    })
    console.log("after fetch");
    
})

const tran = document.getElementById("tran");
const car = document.getElementById("car");
// const walking = document.getElementById("walking");
const dirTran = document.getElementById("dir-tran");
const dirCar = document.getElementById("dir-car");

if (tran.classList.contains("active")){
    dirTran.classList.remove("d-none");
    dirCar.classList.add("d-none");
} else if (car.classList.contains("active")){
    dirTran.classList.add("d-none");
    dirCar.classList.remove("d-none");
}

tran.addEventListener("click", e=>{
    e.preventDefault();
    tran.classList.add("active");
    car.classList.remove("active");
    dirTran.classList.remove("d-none");
    dirCar.classList.add("d-none");
    fetch(`/odsay/directions?`+ new URLSearchParams(paramTran).toString(), {
        method: "GET",
    }).then(response=>{
        if (response.ok){
            return response.json();
        } else {
            return response.text().then(text=>{
                alert(text);
            })
        }
    }).then(result=>{
        transport(result);
    })
    const mapOption = {
        center: new kakao.maps.LatLng(37.5663, 126.9772),
        level: 3
    }
    map = new kakao.maps.Map(mapContainer, mapOption);
    linePathBound.forEach(point=>{
        bounds.extend(point);
    })
    map.setBounds(bounds);
    bounds = new kakao.maps.LatLngBounds();
})

const dirBus = document.getElementById("dir-bus");
const dirSubway = document.getElementById("dir-subway");
const dirAll = document.getElementById("dir-all");
const countAll = document.getElementById("count-all");
const countBus = document.getElementById("count-bus");
const countSubway = document.getElementById("count-subway");

function transport(result) {
    countAll.innerText = result.subwayBusCount + result.busCount + result.subwayCount;
    countBus.innerText = result.busCount;
    countSubway.innerText = result.subwayCount;

    if (dirAll.classList.contains("active")){
        for (let i = 0; i < result.path.length; i++){
            onePath(result.path, i);
            mapPath(result.path[0]);
            const pathId = document.getElementById(`path${i}`);
            pathId.addEventListener("click", e=>{
                e.preventDefault();
                mapPath(result.path[i]);
            })
        }
    } else if (dirBus.classList.contains("active")){
        let idxMin = result.path.length;
        for (let i=0; i < result.path.length; i++){
            if (result.path[i].pathType===2){
                if (idxMin>i) {idxMin=i;}
                onePath(result.path, i);
                const pathId = document.getElementById(`path${i}`);
                pathId.addEventListener("click", e=>{
                    e.preventDefault();
                    mapPath(result.path[i]);
                })
            }
        }
        mapPath(result.path[idxMin]);
    } else if (dirSubway.classList.contains("active")){
        let idxMin = result.path.length;
        for (let i=0; i < result.path.length; i++){
            if (result.path[i].pathType===1){
                if (idxMin>i) {idxMin=i;}
                onePath(result.path, i);
                const pathId = document.getElementById(`path${i}`);
                pathId.addEventListener("click", e=>{
                    e.preventDefault();
                    mapPath(result.path[i]);
                })
            }
        }
        mapPath(result.path[idxMin]);
    }

    dirAll.addEventListener("click", e=>{
        dirAll.classList.add("active");
        dirSubway.classList.remove("active");
        dirBus.classList.remove("active");
        pathArea.innerHTML=``;
        for (let i = 0; i < result.path.length; i++){
            onePath(result.path, i);
            const pathId = document.getElementById(`path${i}`);
            pathId.addEventListener("click", e=>{
                e.preventDefault();
                mapPath(result.path[i]);
            })
        }
    })
    dirBus.addEventListener("click", e=>{
        dirBus.classList.add("active");
        dirSubway.classList.remove("active");
        dirAll.classList.remove("active");
        pathArea.innerHTML=``;
        for (let i=0; i < result.path.length; i++){
            if (result.path[i].pathType===2){
                onePath(result.path, i);
                const pathId = document.getElementById(`path${i}`);
                pathId.addEventListener("click", e=>{
                    e.preventDefault();
                    mapPath(result.path[i]);
                })
            }
        }

    })
    dirSubway.addEventListener("click", e=>{
        dirSubway.classList.add("active");
        dirBus.classList.remove("active");
        dirAll.classList.remove("active");
        pathArea.innerHTML=``;
        for (let i=0; i < result.path.length; i++){
            if (result.path[i].pathType===1){
                onePath(result.path, i);
                const pathId = document.getElementById(`path${i}`);
                pathId.addEventListener("click", e=>{
                    e.preventDefault();
                    mapPath(result.path[i]);
                })
            }
        }
    })
}
const pathArea = document.getElementById("path-area");

// progress, display Route
function onePath(allPath, idx){
    const divMain = document.createElement("div");
    divMain.className = "p-4 border rounded mb-2";
    divMain.id = `path${idx}`;

        const divOne = document.createElement("div");
        divOne.className = "w-100 border-bottom pb-4";
        const divTwo = document.createElement("div");
        divTwo.className = "w-100 column-gap-2";

            const divOneIn = document.createElement("div");
            divOneIn.className = "hstack d-flex align-items-center mb-3";
            divOneIn.innerHTML=
            `
            <div class="me-2">
                <span><b>${allPath[idx].info.totalTime}</b> 분</span>
            </div>
            <div class="vr me-2"></div>
            <div class="">
                <span><b>${allPath[idx].info.payment}</b>₩</span>
            </div>
            `;
            divOne.appendChild(divOneIn);

            const divOneIn2 = document.createElement("div");
            const diIn = document.createElement("div");
            diIn.id=`progress${idx}`;
            diIn.className = "progress-stacked";
            divOneIn2.appendChild(diIn);
            divOne.appendChild(divOneIn2);

            const divTwoIn = document.createElement("div");
            divTwoIn.className = "col-12 d-flex justify-content-between flex-wrap";
            divTwoIn.id = `dis-route${idx}`;
            divTwo.appendChild(divTwoIn);
        divMain.appendChild(divOne);
        divMain.appendChild(divTwo);
    pathArea.appendChild(divMain);

        const progress = document.getElementById(`progress${idx}`);
        const disRoute = document.getElementById(`dis-route${idx}`);
        document.getElementById(`progress${idx}`).innerHTML = ``;
        document.getElementById(`dis-route${idx}`).innerHTML = ``;
        
        let markers = new Array();
        // One subPath
        const allSubPath = allPath[idx].subPath;
        for (let i=0; i<allSubPath.length; i++){
            markers.forEach(marker=>{
                marker.setMap(null);
            })
            markers = new Array();
            if (allSubPath[i].trafficType === 3) {
                            const div = document.createElement("div");
                            div.className = "w-100 text-secondary";
                            if (i<1){
                                div.innerHTML =
                                `
                                <i class="fa-solid fa-person-walking"></i>
                                <span>${departure.title}</span>
                                `;
                            } else {
                                    div.innerHTML =
                                    `
                                   <i class="fa-solid fa-person-walking"></i>
                                   <span>${allSubPath[i-1].endName}</span>
                                   `;
                            }

                            const divVr = document.createElement("div");
                            divVr.className = "w-100";
                            divVr.innerHTML = `<span class="vr"></span>`;
                            disRoute.appendChild(div);
                            disRoute.appendChild(divVr);

                            // calculate time
                            const divProgress = document.createElement("div");
                            divProgress.className = "progress";
                            divProgress.role = "progressbar";
                            const time = Number(allSubPath[i].sectionTime)/Number(allPath[idx].info.totalTime)*100;
                            divProgress.style= `width: ${time}%`;
                            const divSub = document.createElement("div");
                            divSub.className = "progress-bar bg-light position-relative"
                            divSub.innerHTML =
                            `
                            <span class="position-absolute top-50 start-0 translate-middle p-1 ms-2 bg-secondary border rounded-circle">
                                <i class="fa-solid fa-person-walking fa-sm"></i>
                            </span>
                            <div class="w-100 ms-2 text-dark" style="font-size: x-small;">${allSubPath[i].sectionTime}분</div>
                            `
                            divProgress.appendChild(divSub);
                            progress.appendChild(divProgress);
                        }
            else if (allSubPath[i].trafficType === 2) {
                const div = document.createElement("div");
                            div.className = "w-100";
                            div.innerHTML =
                                `
                            <i class="fa-solid fa-bus-simple"></i>
                            <span>${allSubPath[i].startName}</span>
                            `;
                    disRoute.appendChild(div);
                            const divVr = document.createElement("div");
                            divVr.className = "w-100 d-flex flex-wrap";
                            divVr.innerHTML =
                            `
                            <div class="col-1 vr"></div>
                            <div class="ms-5 col-9" id="bus-type${idx}${i}">

                            </div>
                            `;
                            disRoute.appendChild(divVr);
                            const busType = document.getElementById(`bus-type${idx}${i}`);
                
                            allSubPath[i].lane.forEach(oneLane=>{
                                const divBus = document.createElement("div");
                                colorBus(divBus, oneLane);
                                divBus.innerHTML =
                                `
                                <i class="fa-solid fa-bus-simple"></i>
                                <span>${oneLane.busNo}</span><br>
                                `
                                busType.appendChild(divBus);
                            })
                            busType.appendChild(document.createElement("br"));

                            // calculate time
                            const divProgress = document.createElement("div");
                            divProgress.className = "progress";
                            divProgress.role = "progressbar";
                            const time = Number(allSubPath[i].sectionTime)/Number(allPath[idx].info.totalTime)*100;
                            divProgress.style= `width: ${time}%`;
                            const divSub = document.createElement("div");
                            divSub.className = "progress-bar bg-light position-relative"

                                const span = document.createElement("span");
                                span.className= "position-absolute top-50 start-0 translate-middle p-1 ms-2 rounded-circle";
                                bgColorBus(span, allSubPath[i].lane[0]);
                                span.innerHTML=`<i class="fa-solid fa-bus-simple"></i>`;
                            divSub.appendChild(span);
                                const divInside = document.createElement("div");
                                divInside.className = "w-100 ms-2 text-white";
                                bgColorBus(divInside, allSubPath[i].lane[0]);
                                divInside.innerText = `${allSubPath[i].sectionTime}분`;
                            divSub.appendChild(divInside);
                            divProgress.appendChild(divSub);
                            progress.appendChild(divProgress);
                            // set marker
//                            const imageSrc = "/static/markers/markerBus.png";
//                            const imageSize = new kakao.maps.Size(24, 35);
//                            const markerStart = new kakao.maps.MarkerImage(imageSrc, imageSize);
//
//                            const markerBus = new kakao.maps.Marker({
//                                position: new kakao.maps.LatLng(allSubPath[i].startY, allSubPath[i].startX), // 마커의 위치 설정
//                                image: markerStart
//                            });
//
//                            markerBus.setMap(map);
//                            markers.push(markerBus);
                        }
            else if (allSubPath[i].trafficType === 1) {
                            const div = document.createElement("div");
                            div.className = "w-100";
                            div.innerHTML =
                                `
                            <i class="fa-solid fa-train-subway"></i>
                            <span>${allSubPath[i].startName}</span>
                            `;
                            disRoute.appendChild(div);
                            const divVr = document.createElement("div");
                            divVr.className = "w-100 d-flex flex-wrap";
                            divVr.innerHTML =
                            `
                            <div class="col-1 vr"></div>
                            <div class="ms-5 col-9" id="subway-type${idx}${i}">

                            </div>
                            `;
                            disRoute.appendChild(divVr);
                            const subwayType = document.getElementById(`subway-type${idx}${i}`);
                            allSubPath[i].lane.forEach(oneLane=>{
                                const divSubway = document.createElement("div");
                                colorSubway(divSubway, oneLane);
                                divSubway.innerHTML =
                                `
                                <i class="fa-solid fa-train-subway"></i>
                                <span>${oneLane.name}</span><br>
                                `
                                subwayType.appendChild(divSubway);
                            })
                            subwayType.appendChild(document.createElement("br"));

                            // calculate time
                            const divProgress = document.createElement("div");
                            divProgress.className = "progress";
                            divProgress.role = "progressbar";
                            const time = Number(allSubPath[i].sectionTime)/Number(allPath[idx].info.totalTime)*100;
                            divProgress.style= `width: ${time}%`;
                            const divSub = document.createElement("div");
                            divSub.className = "progress-bar bg-light position-relative"

                                const span = document.createElement("span");
                                span.className= "position-absolute top-50 start-0 translate-middle p-1 ms-2 rounded-circle";
                                bgColorSubway(span, allSubPath[i].lane[0]);
                                span.innerHTML=`<i class="fa-solid fa-train-subway"></i>`;
                            divSub.appendChild(span);
                                const divInside = document.createElement("div");
                                divInside.className = "w-100 ms-2 text-white";
                                bgColorSubway(divInside, allSubPath[i].lane[0]);
                                divInside.innerText = `${allSubPath[i].sectionTime}분`;
                            divSub.appendChild(divInside);
                            divProgress.appendChild(divSub);
                            progress.appendChild(divProgress);

                            // set marker
//                            const imageSrc = "/static/marker/markerSubway.png";
//                            const imageSize = new kakao.maps.Size(24, 35);
//                            const markerStart = new kakao.maps.MarkerImage(imageSrc, imageSize);
//
//                            markerSubway = new kakao.maps.Marker({
//                                position: new kakao.maps.LatLng(allSubPath[i].startY, allSubPath[i].startX), // 마커의 위치 설정
//                                image: markerStart
//                            });
//
//                            markerSubway.setMap(map);
//                            markers.push(markerSubway);
                        }
        }

        const divFinish = document.createElement("div");
        divFinish.innerHTML =
            `
            <i class="fa-solid fa-flag"></i>
            <span id="finish-point">${arrival.title}</span>
            `;
        disRoute.appendChild(divFinish);
    
}

function colorBus(i, oneLane){
    if (oneLane.type===12){
        i.style = "color: #12AD2B;";
    } else if (oneLane.type ===11 || oneLane.type===2){
        i.style = "color: #2916F5;";
    } else if (oneLane.type ===4 || oneLane.type===15
        || oneLane.type ===14 || oneLane.type ===6
    ){
        i.style = "color: #EB5406;";
    } else if (oneLane.type ===5){
        i.style = "color: #A0CFEC;";
    } else if (oneLane.type ===13 || oneLane.type ===16){
        i.style = "color: #FFFF00;";
    } else {
        i.style = "color: #BDF516;";
    }
}

function bgColorBus(i, oneLane){
    if (oneLane.type===12){
        i.style = "font-size: x-small; background-color: #12AD2B;";
    } else if (oneLane.type ===11 || oneLane.type===2){
        i.style = "font-size: x-small; background-color: #2916F5;";
    } else if (oneLane.type ===4 || oneLane.type===15
        || oneLane.type ===14 || oneLane.type ===6
    ){
        i.style = "font-size: x-small; background-color: #EB5406;";
    } else if (oneLane.type ===5){
        i.style = "font-size: x-small; background-color: #A0CFEC;";
    } else if (oneLane.type ===13 || oneLane.type ===16){
        i.style = "font-size: x-small; background-color: #FFFF00;";
    } else {
        i.style = "font-size: x-small; background-color: #BDF516;";
    }
}

function colorSubway(i, oneLane){
    if (oneLane.subwayCode===1){
        i.style = "color: #00008B;";
    } else if (oneLane.subwayCode===2){
        i.style = "color: #1AA260;";
    } else if (oneLane.subwayCode===3){
        i.style = "color: #FFA500;";
    } else if (oneLane.subwayCode===4){
        i.style = "color: #3BB9FF;";
    } else if (oneLane.subwayCode===5){
        i.style = "color: #C45AEC;";
    } else if (oneLane.subwayCode===6){
        i.style = "color: #CA762B;";
    } else if (oneLane.subwayCode===7){
        i.style = "color: #665D1E;";
    } else if (oneLane.subwayCode===8){
        i.style = "color: #FF1493;";
    } else if (oneLane.subwayCode===9){
        i.style = "color: #EAC117;";
    } else {
        i.style = "color: #A0CFEC;";
    }
}
function bgColorSubway(i, oneLane){
    if (oneLane.subwayCode===1){
        i.style = "font-size: x-small; background-color: #00008B;";
    } else if (oneLane.subwayCode===2){
        i.style = "font-size: x-small; background-color: #1AA260;";
    } else if (oneLane.subwayCode===3){
        i.style = "font-size: x-small; background-color: #FFA500;";
    } else if (oneLane.subwayCode===4){
        i.style = "font-size: x-small; background-color: #3BB9FF;";
    } else if (oneLane.subwayCode===5){
        i.style = "font-size: x-small; background-color: #C45AEC;";
    } else if (oneLane.subwayCode===6){
        i.style = "font-size: x-small; background-color: #CA762B;";
    } else if (oneLane.subwayCode===7){
        i.style = "font-size: x-small; background-color: #665D1E;";
    } else if (oneLane.subwayCode===8){
        i.style = "font-size: x-small; background-color: #FF1493;";
    } else if (oneLane.subwayCode===9){
        i.style = "font-size: x-small; background-color: #EAC117;";
    } else {
        i.style = "font-size: x-small; background-color: #A0CFEC;";
    }
}


function mapPath(path){
    linePathBound = new Array();

    const mapObj = path.info.mapObj;
    fetch(`/odsay/loadLane?mapObj=${mapObj}`,{
        method: "GET",
    }).then(response => {
        if (response.ok){
            return response.json();
        } else {
            response.text().then(text=>{
                alert(text);
            })
        }
    }). then(data=>{
        drawPolyLine(data);
    })

    fetch("/n-cloud?" + new URLSearchParams(paramStart(path.subPath)).toString(), {
        method: "GET",
        headers: {
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
        }
    }).then(response => {
        if (response.ok){return response.json()}
        else {
            return response.text().then(text=> {alert(text)})
        }
    }).then(data => {
        nCloudStart(data);
    });

    fetch("/n-cloud?" + new URLSearchParams(paramFinish(path.subPath)).toString(), {
        method: "GET",
        headers: {
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
        }
    }).then(response => {
        if (response.ok){return response.json()}
        else {
            return response.text().then(text=> {alert(text)})
        }
    }).then(data => {
        nCloudFinish(data);
    });
}

let polylines = new Array();
let borders = new Array();
function drawPolyLine(data){
    if (polylineCar) {
        border1.setMap(null);
        polylineCar.setMap(null);
    }
    polylines.forEach(polyline => {
        polyline.setMap(null);
    })
    borders.forEach(border2=>{
        border2.setMap(null);
    })
    polylines = new Array();
    borders = new Array();
    let lineArray;
    let polyline;
    let border2;
    for(let i = 0 ; i < data.lane.length; i++){
        for(let j=0 ; j <data.lane[i].section.length; j++){
            lineArray = new Array();
            for (let k=0 ; k < data.lane[i].section[j].graphPos.length; k++){
                lineArray.push(new kakao.maps.LatLng(data.lane[i].section[j].graphPos[k].y, data.lane[i].section[j].graphPos[k].x));
            }
            
            if (data.lane[i].class === 1){
                border2 = new kakao.maps.Polyline({
                    path: lineArray,
                    strokeColor: '#000000',  // Dark color for border
                    strokeWeight: 5,  // Slightly larger stroke for border
                    strokeOpacity: 0.7,  // Semi-transparent border
                    zIndex: 0  // Behind the main line
                  });
                polyline = new kakao.maps.Polyline({
                    path: lineArray,
                    strokeWeight: 3,
                    strokeOpacity: 1, // The opacity of the polyline
                    strokeStyle: 'solid',
                    strokeColor: colorBusMap(data.lane[i]),
                    zIndex: 1
                });
                polyline.setMap(map);
                border2.setMap(map);
                
            }
            else if (data.lane[i].class === 2) {
                border2 = new kakao.maps.Polyline({
                    path: lineArray,
                    strokeColor: '#000000',  // Dark color for border
                    strokeWeight: 5,  // Slightly larger stroke for border
                    strokeOpacity: 0.7,  // Semi-transparent border
                    zIndex: 0  // Behind the main line
                  });
                polyline = new kakao.maps.Polyline({
                    path: lineArray,
                    strokeWeight: 3,
                    strokeOpacity: 1, // The opacity of the polyline
                    strokeStyle: 'solid',
                    strokeColor: colorSubwayMap(data.lane[i]),
                    zIndex:1
                });
                polyline.setMap(map);
                border2.setMap(map);
                
            } else {
                border2 = new kakao.maps.Polyline({
                    path: lineArray,
                    strokeColor: '#000000',  // Dark color for border
                    strokeWeight: 5,  // Slightly larger stroke for border
                    strokeOpacity: 0.7,  // Semi-transparent border
                    zIndex: 0  // Behind the main line
                  });
                polyline = new kakao.maps.Polyline({
                    path: lineArray,
                    strokeWeight: 3,
                    strokeOpacity: 1, // The opacity of the polyline
                    strokeStyle: 'solid',
                    zIndex: 1
                });
                polyline.setMap(map);
                border2.setMap(map);
            }
            polylines.push(polyline);
            borders.push(border2);
        }
    }
}

function colorBusMap(oneLane){
    if (oneLane.type===12){
        return "#12AD2B";
    } else if (oneLane.type ===11 || oneLane.type===2){
        return "#2916F5";
    } else if (oneLane.type ===4 || oneLane.type===15
        || oneLane.type ===14 || oneLane.type ===6
    ){
        return "#EB5406";
    } else if (oneLane.type ===5){
        return "#A0CFEC";
    } else if (oneLane.type ===13 || oneLane.type ===16){
        return "#FFFF00";
    } else {
        return "#BDF516";
    }
}

function colorSubwayMap(oneLane){
    if (oneLane.type===1){
        return "#00008B";
    } else if (oneLane.type===2){
        return "#1AA260";
    } else if (oneLane.type===3){
        return "#FFA500";
    } else if (oneLane.type===4){
        return "#3BB9FF";
    } else if (oneLane.type===5){
        return "#C45AEC";
    } else if (oneLane.type===6){
        return "#CA762B";
    } else if (oneLane.type===7){
        return "#665D1E";
    } else if (oneLane.type===8){
        return "#FF1493";
    } else if (oneLane.type===9){
        return "#EAC117";
    } else {
        return "#A0CFEC";
    }
}

function paramStart(allSubPath){
    let param;
    if (allSubPath[0].trafficType === 3){
        param = {
            "sx": departure.mapx,
            "sy": departure.mapy,
            "ex": allSubPath[1].startX,
            "ey": allSubPath[1].startY,
            "option": "traavoidcaronly"
        }
        
    } else {
        param = {
            "sx": departure.mapx,
            "sy": departure.mapy,
            "ex": allSubPath[0].startX,
            "ey": allSubPath[0].startY,
            "option": "traavoidcaronly"
        }
    }
    return param;
}

function paramFinish(allSubPath){
    let param;
    if (allSubPath[allSubPath.length-1].trafficType === 3){
        param = {
            "sx": allSubPath[allSubPath.length-2].endX,
            "sy": allSubPath[allSubPath.length-2].endY,
            "ex": arrival.mapx,
            "ey": arrival.mapy,
            "option": "traavoidcaronly"
        }
    } else {
        param = {
            "sx": allSubPath[allSubPath.length-1].endX,
            "sy": allSubPath[allSubPath.length-1].endY,
            "ex": arrival.mapx,
            "ey": arrival.mapy,
            "option": "traavoidcaronly"
        }
    }
    return param;
}

let linePathBound = [];
let polylineStart;
let linePathStart;
function nCloudStart(data){
    linePathStart= new Array();
    if (polylineStart) {polylineStart.setMap(null);}

    data.path.forEach((coordinate, index) => {
        if (index % 2 === 0) {
            linePathStart.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
            linePathBound.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
        }
    });
    // const border6 = new kakao.maps.Polyline({
    //     path: linePathStart,
    //     strokeColor: '#000000',  // Dark color for border
    //     strokeWeight: 5,  // Slightly larger stroke for border
    //     strokeOpacity: 0.7,  // Semi-transparent border
    //     zIndex: 0  // Behind the main line
    //   });
    polylineStart = new kakao.maps.Polyline({
                        path: linePathStart,
                        strokeWeight: 5,
                        strokeColor: '#040720',
                        strokeOpacity: 1,
                        strokeStyle: 'dot',
                        zIndex: 1
                      });
                polylineStart.setMap(map);
                // border6.setMap(map);
}

let polylineFinish;
let linePathFinish;
function nCloudFinish(data){
    linePathFinish = new Array();
    if(polylineFinish) {polylineFinish.setMap(null);}
        data.path.forEach((coordinate, index) => {
            if (index % 2 === 0) {
                linePathFinish.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
                linePathBound.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
            }
        });
        // const border5 = new kakao.maps.Polyline({
        //     path: linePathFinish,
        //     strokeColor: '#000000',  // Dark color for border
        //     strokeWeight: 5,  // Slightly larger stroke for border
        //     strokeOpacity: 0.7,  // Semi-transparent border
        //     zIndex: 0  // Behind the main line
        //   });
        polylineFinish = new kakao.maps.Polyline({
            path: linePathFinish,
            strokeWeight: 5,
            strokeColor: '#040720',
            strokeOpacity: 1,
            strokeStyle: 'dot',
            zIndex: 1
        });
        polylineFinish.setMap(map);
        // border5.setMap(map);
}

const carTime1 = document.getElementById("car-time1");
const carTime2 = document.getElementById("car-time2");
const carTime3 = document.getElementById("car-time3");

const distance1 = document.getElementById("distance1");
const distance2 = document.getElementById("distance2");
const distance3 = document.getElementById("distance3");

const taxiFare1 = document.getElementById("taxiFare1");
const taxiFare2 = document.getElementById("taxiFare2");
const taxiFare3 = document.getElementById("taxiFare3");

const tollFare1 = document.getElementById("tollFare1");
const tollFare2 = document.getElementById("tollFare2");
const tollFare3 = document.getElementById("tollFare3");

const fuelPrice1 = document.getElementById("fuelPrice1");
const fuelPrice2 = document.getElementById("fuelPrice2");
const fuelPrice3 = document.getElementById("fuelPrice3");


// Display  car
function paramCar(option){
    const param = {
        "sx": departure.mapx,
        "sy": departure.mapy,
        "ex": arrival.mapx,
        "ey": arrival.mapy,
        "option": option,
    }
    return param;
}

let polylineCar;
let border1;
function nCloudMap(data){
    polylines.forEach(polyline => {
        polyline.setMap(null);
    })
    borders.forEach(border2=>{
        border2.setMap(null);
    })
    polylines = new Array();
    borders= new Array();
    polylineStart.setMap(null);
    polylineFinish.setMap(null);
    if(polylineCar) {
        border1.setMap(null);
        polylineCar.setMap(null);
    }
    const linePath = new Array();
    data.path.forEach((coordinate, index) => {
    if (index % 2 === 0) {
        linePath.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
        }
    });
    border1 = new kakao.maps.Polyline({
        path: linePath,
        strokeColor: '#000000',  // Dark color for border
        strokeWeight: 5,  // Slightly larger stroke for border
        strokeOpacity: 0.7,  // Semi-transparent border
        zIndex: 0  // Behind the main line
      });
    polylineCar = new kakao.maps.Polyline({
        path: linePath,
        strokeWeight: 3,
        strokeColor: '#999999',
        strokeOpacity: 1,
        strokeStyle: 'solid',
        zIndex: 1 
    });
    polylineCar.setMap(map);
    border1.setMap(map);
    bounds.extend(new kakao.maps.LatLng(departure.mapy, departure.mapx));
    bounds.extend(new kakao.maps.LatLng(arrival.mapy, arrival.mapx));
    map.setBounds(bounds);
    bounds = new kakao.maps.LatLngBounds();
}

const car1 = document.getElementById("car1");
const car2 = document.getElementById("car2");
const car3 = document.getElementById("car3");

car.addEventListener("click", e=>{
    e.preventDefault();
    tran.classList.remove("active");
    car.classList.add("active");
    dirTran.classList.add("d-none");
    dirCar.classList.remove("d-none");

    fetch("/n-cloud?" + new URLSearchParams(paramCar("traoptimal")).toString(), {
        method: "GET",
        headers: {
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
        }
    }).then(response => {
        if (response.ok){return response.json()}
        else {
            return response.text().then(text=> {alert(text)})
        }
    }).then(data => {
        carTime1.innerText = Math.round(data.summary.duration/60000);
        distance1.innerText = Math.round(data.summary.distance/1000, 1);
        taxiFare1.innerText = data.summary.taxiFare;
        if (data.summary.tollFare === 0) {
            tollFare1.innerText = "무료";
        } else tollFare1.innerText = data.summary.tollFare+"₩";
        fuelPrice1.innerText = data.summary.fuelPrice;
        nCloudMap(data);
        car1.addEventListener("click", e=>{
            e.preventDefault();
            nCloudMap(data);
        })
    })

    fetch("/n-cloud?" + new URLSearchParams(paramCar("traavoidtoll")).toString(), {
        method: "GET",
        headers: {
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
        }
    }).then(response => {
        if (response.ok){return response.json()}
        else {
            return response.text().then(text=> {alert(text)})
        }
    }).then(data => {
        carTime2.innerText = Math.round(data.summary.duration/60000);
        distance2.innerText = Math.round(data.summary.distance/1000, 1);
        taxiFare2.innerText = data.summary.taxiFare;
        tollFare2.innerText = "무료";
        fuelPrice2.innerText = data.summary.fuelPrice;
        nCloudMap(data);
        car2.addEventListener("click", e=>{
            e.preventDefault();
            nCloudMap(data);
        })
    })
    
    fetch("/n-cloud?" + new URLSearchParams(paramCar("traavoidcaronly")).toString(), {
        method: "GET",
        headers: {
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
        }
    }).then(response => {
        if (response.ok){return response.json()}
        else {
            return response.text().then(text=> {alert(text)})
        }
    }).then(data => {
        carTime3.innerText = Math.round(data.summary.duration/60000);
        distance3.innerText = Math.round(data.summary.distance/1000, 1);
        taxiFare3.innerText = data.summary.taxiFare;
        if (data.summary.tollFare === 0) {
            tollFare3.innerText = "무료";
        } else tollFare3.innerText = data.summary.tollFare+"₩";
        fuelPrice3.innerText = data.summary.fuelPrice;
        nCloudMap(data);
        car3.addEventListener("click", e=>{
            e.preventDefault();
            nCloudMap(data);
        })
    })
})



        