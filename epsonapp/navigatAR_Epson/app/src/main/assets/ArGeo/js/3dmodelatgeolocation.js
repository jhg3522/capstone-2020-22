var config = {
        apiKey: "AIzaSyBZEM5WsbwS5frsqgpvxZMxMW1ZCaTV4oE",
        authDomain: "navi-9e6bf.firebaseapp.com",
        databaseURL: "https://navi-9e6bf.firebaseio.com",
        projectId: "navi-9e6bf",
        storageBucket: "navi-9e6bf.appspot.com",
        appId: "1:15852780522:web:f9299965f2b215a7995093",
    };
    firebase.initializeApp(config);

var World = {




    init: function initFn() {
        this.createModelAtLocation();
    },

    createModelAtLocation: function createModelAtLocationFn() {

        /*
            First a location where the model should be displayed will be defined. This location will be relative to
            the user.
        */

//        var glassNum = "<%=glassNum%>";
        var glassNum = 1;
        firebase.database().ref('users/').orderByChild('num').equalTo(glassNum).once('value', function(snapshot){
            snapshot.forEach(function(snap){
                var glassKey = snap.key;

                var destinationRef = firebase.database().ref('users/').child(String(glassKey)).child('destination');

                var lat;
                var lng;

                destinationRef.on('value',function(data){
                    var lat_ref = destinationRef.child('/latitude');
                    var lng_ref = destinationRef.child('/longitude');
                    lat_ref.on('value',function(child){
                         lat = child.val();
                         console.log(lat);
                    });
                    lng_ref.on('value',function(child){
                         lng = child.val();
                         console.log(lng);
                    });
                    var location = new AR.GeoLocation(lat, lng);
                    /* Next the model object is loaded. */
                    var modelEarth = new AR.Model("assets/here.wt3", {
                         onError: World.onError,
                         scale: {
                             x: 0.01,
                             y: 0.01,
                             z: 0.01
                         },
                         rotate: {
                             x: 0,
                             y: 0
                         }
                    });

                    var indicatorImage = new AR.ImageResource("assets/indi.png", {
                         onError: World.onError
                    });

                    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                         verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                    });

                     /* Putting it all together the location and 3D model is added to an AR.GeoObject. */
                    this.geoObject = new AR.GeoObject(location, {
                         drawables: {
                             cam: [modelEarth],
                             indicator: [indicatorDrawable]
                         }
                    });
                });

            })

        })
    },

    onError: function onErrorFn(error) {
        alert(error);
    }
};

World.init();