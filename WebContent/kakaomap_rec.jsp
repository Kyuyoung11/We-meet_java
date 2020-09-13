<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ī�װ��� ��� �˻��ϱ�</title>
    
</head>
<body>
<input type="text" id="mapPointx" onchange="change()">

<div id="map" style="width:100%;height:500px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8db9dff95416858f6139281500bb3bd3&libraries=services"></script>
<script type="text/javascript">
function change(input1,input2,input3) {
	var mapPointx = document.getElementById('mapPointx').value;
	// ��Ŀ�� Ŭ���ϸ� ��Ҹ��� ǥ���� ���������� �Դϴ�
	var infowindow = new kakao.maps.InfoWindow({zIndex:1});
	
	
	var mapContainer = document.getElementById('map'), // ������ ǥ���� div 
	    mapOption = {
	        center: new kakao.maps.LatLng(input1, input2), // ������ �߽���ǥ
	        level: 4 // ������ Ȯ�� ����
	    };  
	
	// ������ �����մϴ�    
	var map = new kakao.maps.Map(mapContainer, mapOption); 
	
	
	
	// ��� �˻� ��ü�� �����մϴ�
	var ps = new kakao.maps.services.Places(map); 
	
	// ī�װ��� ������ �˻��մϴ�
	var cateCode;
	//var category = "����";
	if (input3 === "����") { cateCode = 'FD6'; }
	else if (input3 === "�������") { cateCode = 'AT4'; }
	else if (input3 === "ī��") { cateCode = 'CE7'; }
	ps.categorySearch(cateCode, placesSearchCB, {useMapBounds:true}); 
	
	// Ű���� �˻� �Ϸ� �� ȣ��Ǵ� �ݹ��Լ� �Դϴ�
	function placesSearchCB (data, status, pagination) {
	    if (status === kakao.maps.services.Status.OK) {
	        for (var i=0; i<data.length; i++) {
	            displayMarker(data[i]);    
	        }       
	    }
	}
	
	// ������ ��Ŀ�� ǥ���ϴ� �Լ��Դϴ�
	function displayMarker(place) {
	    // ��Ŀ�� �����ϰ� ������ ǥ���մϴ�
	    var marker = new kakao.maps.Marker({
	        map: map,
	        position: new kakao.maps.LatLng(place.y, place.x) 
	    });
	
	    // ��Ŀ�� Ŭ���̺�Ʈ�� ����մϴ�
	    kakao.maps.event.addListener(marker, 'click', function() {
	        // ��Ŀ�� Ŭ���ϸ� ��Ҹ��� ���������쿡 ǥ��˴ϴ�
	        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
	        infowindow.open(map, marker);
	    });
	}
}

</script>
</body>
</html>