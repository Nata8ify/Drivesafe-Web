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
                                    feedBodyMessage = " ขอความช่วยเหลือที่ ".concat(place);
                                    break;
                                case "G" :
                                    feedBodyMessage = " เจ้าหน้าที่กำลังเดินทางไปช่วยที่ ".concat(place);
                                    break;
                                case "R" :
                                    feedBodyMessage = " เจ้าที่หน้าที่กำลังช่วยเหลือผู้ประสบภัยที่ ".concat(place);
                                    break;
                                case "C" :
                                    feedBodyMessage = " การช่วยเหลือเสร็จสิ้น สถานการณ์ปลอดภัย ";
                                    break;
                                case "U" :
                                    feedBodyMessage = " ยกเลิกการขอความช่วยเหลือ ";
                                    break;
                                case "S" :
                                    feedBodyMessage = " รายงานว่าเป็นอุบัติเหตุ/เหตุร้าย ที่ไม่ได้เกิดขึ้นจริง ";
                                    break;
                            }
                        } else {
                            place = "การเชื่อมต่อผิดพลาด";
                        }
                        feedContent = $("<span href='#' class='list-group-item list-group-item-action'><div class='media'><img class='d-flex mr-3 rounded-circle' src='image/color/"+feed.updatedAccCode.toLowerCase()+".PNG' width='50px' alt=''><div class='media-body'><strong>" + ((feed.updatedAccCode === 'A' || feed.updatedAccCode === 'U') ? feed.reporterName : feed.rscrName) + "</strong>" + feedBodyMessage + "<div class='text-muted smaller'>" + feed.timestamp + "</div></div></div> </span>");

                        $("#append-feed").append(feedContent);
                    }
                });
            });
        }
    });
}
