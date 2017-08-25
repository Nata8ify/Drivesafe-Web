/* Feed Here (Below)  */
var feeds;
setTimeout(function () {
    getFeeds();
    setInterval(getFeeds, 10000);
}, 10);
function getFeeds() {
    feeds = [];
    $.ajax({
        url: "DashboardFeed?opt=get",
        success: function (resultFeeds) {
            feeds = JSON.parse(resultFeeds);
            var feedBodyMessage;
            var feedContent;
            $.each(feeds, function (index, feed) {
                $("#append-feed").empty();
                console.log(feed);
                $.ajax({
                    "url": "http://maps.googleapis.com/maps/api/geocode/json",
                    "data": {"sensor": true, "latlng": (feed.accident.latitude) + "," + (feed.accident.longitude)},
                    "success": function (result) {
                        if (result.status == "OK") {
                            var addrComponent = result.results[0].address_components;
                            place = (addrComponent[0].long_name.concat(", ".concat(addrComponent[1].long_name)).concat(", ".concat(addrComponent[2].long_name)).concat(", ".concat(addrComponent[3].long_name)).concat(", ".concat(addrComponent[5].long_name)));
                            switch (feed.updatedAccCode) {
                                case "A" :
                                    feedBodyMessage = " is requesting for rescuing at ".concat(place);
                                    break;
                                case "G" :
                                    feedBodyMessage = " is going for rescuing at ".concat(place);
                                    break;
                                case "R" :
                                    feedBodyMessage = " is already in place ".concat(place);
                                    break;
                                case "C" :
                                    feedBodyMessage = " close this operation.";
                                    break;
                                case "U" :
                                    feedBodyMessage = " set his/her incident to false negative.";
                                    break;
                                case "S" :
                                    feedBodyMessage = " report for false incident/accident.";
                                    break;
                            }
                        } else {
                            place = "[Connection Error]";
                        }
                        feedContent = $("<span href='#' class='list-group-item list-group-item-action'><div class='media'><img class='d-flex mr-3 rounded-circle' src='http://placehold.it/45x45' alt=''><div class='media-body'><strong>" + ((feed.updatedAccCode === 'A' || feed.updatedAccCode === 'U') ? feed.reporterName : feed.rscrName) + "</strong>" + feedBodyMessage + "<div class='text-muted smaller'>" + feed.timestamp + "</div></div></div> </span>");

                        $("#append-feed").append(feedContent);
                    }
                });
            });
        }
    });
}
