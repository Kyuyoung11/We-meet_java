<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>���� �����ϱ�</title>
    
</head>
<body>
<!-- ������ ǥ���� div �Դϴ� -->
<div id="map" style="width:100%;height:500px;"></div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=	8db9dff95416858f6139281500bb3bd3&libraries=services"></script>
<script>

var x = [37.582435, 37.510194, 37.558282, 37.6484962] //���߿� db���� ����� ��ǥ �������°ɷ� �ٲٱ�
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


//���尡��� ����ö�� ���ϱ�
//��Ŀ�� Ŭ���ϸ� ��Ҹ��� ǥ���� ���������� �Դϴ�
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

var mapContainer = document.getElementById('map'), // ������ ǥ���� div 
    mapOption = {
        center: new kakao.maps.LatLng(centerx, centery), // ������ �߽���ǥ
        level: 5 // ������ Ȯ�� ����
    };  

// ������ �����մϴ�    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// ��� �˻� ��ü�� �����մϴ�
var ps = new kakao.maps.services.Places(map); 

//ī�װ��� ����ö���� �˻��մϴ�
ps.categorySearch('SW8', placesSearchCB, {useMapBounds:true}); 

var finalLong=0;
var finalLat=0;
var shortest = 0;
var shortestNum = 0;
var shortest_Name;

//Ű���� �˻� �Ϸ� �� ȣ��Ǵ� �ݹ��Լ� �Դϴ�
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




var mapContainer = document.getElementById('map'), // ������ ǥ���� div 
mapOption = {
    center: new kakao.maps.LatLng(centerx, centery), // ������ �߽���ǥ
    level: 7 // ������ Ȯ�� ����
};  

//������ �����մϴ�    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//��Ŀ�� ǥ���� ��ġ�� title ��ü �迭�Դϴ� 
var positions = [];
for (var i = 0; i < x.length; i++ ) {
	positions.push(
	    {
	        title: '�����' + i, 
	        latlng: new kakao.maps.LatLng(x[i], y[i])
	    });
}


// ��Ŀ �̹����� �̹��� �ּ��Դϴ�
var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
    
for (var i = 0; i < positions.length; i ++) {
    
    // ��Ŀ �̹����� �̹��� ũ�� �Դϴ�
    var imageSize = new kakao.maps.Size(24, 35); 
    
    // ��Ŀ �̹����� �����մϴ�    
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
    
    // ��Ŀ�� �����մϴ�
    var marker = new kakao.maps.Marker({
        map: map, // ��Ŀ�� ǥ���� ����
        position: positions[i].latlng, // ��Ŀ�� ǥ���� ��ġ
        title : positions[i].title, // ��Ŀ�� Ÿ��Ʋ, ��Ŀ�� ���콺�� �ø��� Ÿ��Ʋ�� ǥ�õ˴ϴ�
        image : markerImage // ��Ŀ �̹��� 
    });
}

 

for (var i = 0; i < x.length; i++) {
	//���� �����ϴ� ��ǥ �迭�Դϴ�. �� ��ǥ���� �̾ ���� ǥ���մϴ�
	var linePath = [
	    new kakao.maps.LatLng(x[i], y[i]),
	    new kakao.maps.LatLng(centerx, centery)
	];
	
	// ������ ǥ���� ���� �����մϴ�
	var polyline = new kakao.maps.Polyline({
	    path: linePath, // ���� �����ϴ� ��ǥ�迭 �Դϴ�
	    strokeWeight: 5, // ���� �β� �Դϴ�
	    strokeColor: '#FFAE00', // ���� �����Դϴ�
	    strokeOpacity: 0.7, // ���� ������ �Դϴ� 1���� 0 ������ ���̸� 0�� �������� �����մϴ�
	    strokeStyle: 'solid' // ���� ��Ÿ���Դϴ�
	});
	
	// ������ ���� ǥ���մϴ� 
	polyline.setMap(map); 
}
//������ ǥ���� ���� �����մϴ�
var circle = new kakao.maps.Circle({
    center : new kakao.maps.LatLng(centerx, centery),  // ���� �߽���ǥ �Դϴ� 
    radius: 500, // ���� ������ ���� �������Դϴ� 
    strokeWeight: 3, // ���� �β��Դϴ� 
    strokeColor: '#75B8FA', // ���� �����Դϴ�
    strokeOpacity: 1, // ���� ������ �Դϴ� 1���� 0 ������ ���̸� 0�� �������� �����մϴ�
    strokeStyle: 'line', // ���� ��Ÿ�� �Դϴ�
    fillColor: '#CFE7FF', // ä��� �����Դϴ�
    fillOpacity: 0.7  // ä��� ������ �Դϴ�   
}); 

// ������ ���� ǥ���մϴ� 
circle.setMap(map);

</script>
</body>
</html>