<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>카테고리로 장소 검색하기</title>
    
</head>
<body>
<input type="text" id="mapPointx" onchange="change()">

<div id="map" style="width:100%;height:500px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8db9dff95416858f6139281500bb3bd3&libraries=services"></script>
<script type="text/javascript">
function change(input1,input2,input3) {
	var mapPointx = document.getElementById('mapPointx').value;
	// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
	var infowindow = new kakao.maps.InfoWindow({zIndex:1});
	
	
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new kakao.maps.LatLng(input1, input2), // 지도의 중심좌표
	        level: 4 // 지도의 확대 레벨
	    };  
	
	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	
	
	// 장소 검색 객체를 생성합니다
	var ps = new kakao.maps.services.Places(map); 
	
	// 카테고리로 은행을 검색합니다
	var cateCode;
	//var category = "맛집";
	if (input3 === "맛집") { cateCode = 'FD6'; }
	else if (input3 === "관광명소") { cateCode = 'AT4'; }
	else if (input3 === "카페") { cateCode = 'CE7'; }
	ps.categorySearch(cateCode, placesSearchCB, {useMapBounds:true}); 
	
	// 키워드 검색 완료 시 호출되는 콜백함수 입니다
	function placesSearchCB (data, status, pagination) {
	    if (status === kakao.maps.services.Status.OK) {
	        for (var i=0; i<data.length; i++) {
	            displayMarker(data[i]);    
	        }       
	    }
	}
	
	// 지도에 마커를 표시하는 함수입니다
	function displayMarker(place) {
	    // 마커를 생성하고 지도에 표시합니다
	    var marker = new kakao.maps.Marker({
	        map: map,
	        position: new kakao.maps.LatLng(place.y, place.x) 
	    });
	
	    // 마커에 클릭이벤트를 등록합니다
	    kakao.maps.event.addListener(marker, 'click', function() {
	        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
	        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
	        infowindow.open(map, marker);
	    });
	}
}

</script>
</body>
</html>