<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>지도 생성하기</title>
    
</head>
<body>
<!-- 지도를 표시할 div 입니다 -->
<div id="map" style="width:100%;height:500px;"></div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=	8db9dff95416858f6139281500bb3bd3&libraries=services"></script>
<script>

var x = [37.582435, 37.510194, 37.558282, 37.6484962] //나중에 db에서 사용자 좌표 가져오는걸로 바꾸기
var y = [127.011239, 127.112316, 126.969452, 127.0076599] 
var sumx = 0;
var sumy = 0;
var centerx, centery;
for (var i = 0; i < x.length; i++ ) {
	sumx = sumx + x[i];
	sumy = sumy + y[i];
}
centerx = sumx / x.length;
centery = sumy / x.length;


//가장가까운 지하철역 구하기
//마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(centerx, centery), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(map); 

//카테고리로 지하철역을 검색합니다
ps.categorySearch('SW8', placesSearchCB, {useMapBounds:true}); 

var finalLong=0;
var finalLat=0;
var shortest = 0;
var shortestNum = 0;
var shortest_Name;

//키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status, pagination) {
	if (status === kakao.maps.services.Status.OK) {
		shortest = Math.pow((data[0].y-centerx),2)+ Math.pow((data[0].x-centery),2);
		for (var i=0; i<data.length; i++) {
			if (shortest > 
			(Math.pow((data[i].y-centerx),2)+ Math.pow((data[i].x-centery),2))) {
				shortestNum = i;
				shortest = Math.pow((data[i].y-centerx),2)+ Math.pow((data[i].x-centery),2);
			}
		}
		finalLong = data[shortestNum].x;
		finalLat = data[shortestNum].y;
		shortest_Name = data[shortestNum].place_name;
		window.sendMessage.sendPoint(finalLat, finalLong, shortest_Name);
		var marker = new kakao.maps.Marker({
	        map: map,
	        position: new kakao.maps.LatLng(finalLat, finalLong) 
	    });
		
	}
}




var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = {
    center: new kakao.maps.LatLng(centerx, centery), // 지도의 중심좌표
    level: 7 // 지도의 확대 레벨
};  

//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//마커를 표시할 위치와 title 객체 배열입니다 
var positions = [];
for (var i = 0; i < x.length; i++ ) {
	positions.push(
	    {
	        title: '사용자' + i, 
	        latlng: new kakao.maps.LatLng(x[i], y[i])
	    });
}


// 마커 이미지의 이미지 주소입니다
var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
    
for (var i = 0; i < positions.length; i ++) {
    
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35); 
    
    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
    
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: positions[i].latlng, // 마커를 표시할 위치
        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
    });
}

 

for (var i = 0; i < x.length; i++) {
	//선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다
	var linePath = [
	    new kakao.maps.LatLng(x[i], y[i]),
	    new kakao.maps.LatLng(centerx, centery)
	];
	
	// 지도에 표시할 선을 생성합니다
	var polyline = new kakao.maps.Polyline({
	    path: linePath, // 선을 구성하는 좌표배열 입니다
	    strokeWeight: 5, // 선의 두께 입니다
	    strokeColor: '#FFAE00', // 선의 색깔입니다
	    strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
	    strokeStyle: 'solid' // 선의 스타일입니다
	});
	
	// 지도에 선을 표시합니다 
	polyline.setMap(map); 
}
//지도에 표시할 원을 생성합니다
var circle = new kakao.maps.Circle({
    center : new kakao.maps.LatLng(centerx, centery),  // 원의 중심좌표 입니다 
    radius: 500, // 미터 단위의 원의 반지름입니다 
    strokeWeight: 3, // 선의 두께입니다 
    strokeColor: '#75B8FA', // 선의 색깔입니다
    strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
    strokeStyle: 'line', // 선의 스타일 입니다
    fillColor: '#CFE7FF', // 채우기 색깔입니다
    fillOpacity: 0.7  // 채우기 불투명도 입니다   
}); 

// 지도에 원을 표시합니다 
circle.setMap(map);

</script>
</body>
</html>