(function ($) {
    //    "use strict";

    function loadWeather(location, woeid) {
        $.simpleWeather({
            location: location,
            woeid: woeid,
            unit: 'c',
            success: function (weather) {
            	getLocation();
            	
                html = '<i class="wi wi-yahoo-' + weather.code + '"></i><h2> ' + weather.temp + '&deg;' + weather.units.temp + '</h2>';
                html += '<div class="city">' + weather.city + ', ' + weather.region + '</div>';
                html += '<div class="currently">' + weather.currently + '</div>';
                html += '<div class="celcious">' + weather.alt.temp + '&deg;F</div>';

                $("#weather-one").html(html);
            },
            error: function (error) {
                $("#weather-one").html('<p>' + error + '</p>');
            }
        });
    }


    // init
    loadWeather('San Andreas', '');

})(jQuery);

function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else { 
        console.log("Geolocation is not supported by this browser.");
    }
}

function showPosition(position) {
	//var a = getAddress(position.coords.latitude, position.coords.longitude);
	getAddress(position.coords.latitude, position.coords.longitude).then(console.log).catch(console.error);
	
}

function getAddress (latitude, longitude) {
    return new Promise(function (resolve, reject) {
        var request = new XMLHttpRequest();

        var method = 'GET';
        var url = 'http://maps.googleapis.com/maps/api/geocode/json?latlng=' + latitude + ',' + longitude + '&sensor=true';
        var async = true;

        request.open(method, url, async);
        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                if (request.status == 200) {
                    var data = JSON.parse(request.responseText);
                    var address = data.results[0];
                    resolve(address);
                }
                else {
                    reject(request.status);
                }
            }
        };
        request.send();
    });
};