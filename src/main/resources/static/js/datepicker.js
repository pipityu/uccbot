function addDate() {

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", "https://api.manychat.com/fb/subscriber/getInfo?subscriber_id=3809668825726118", true);
    xmlhttp.setRequestHeader("Authorization", "Bearer 105197630914532:ba342569ac0c5408909eee97f971b9a6");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.onload = function () {
        if (this.readyState == 4 && this.status == 200) {
            var myObj = JSON.parse(this.responseText);
            document.getElementById("leker").innerHTML = myObj.first_name;
            alert("ide jutott");
        }
    };
    xmlhttp.send();


/*        var startDate = $("#startDate").val();
        var endDate =  $("#endDate").val();
        var requestType = $("input[name=radio]:checked", "#dateForm").val();

        if(startDate == "" || endDate == "" || (startDate > endDate) || (typeof requestType == "undefined")) {
            alert("A dátumok vagy a kérelem nem megfelelő, kérlek ellenőrizd!" +
                "");
        }
        else{
                alert(requestType);
                $("#dateForm").attr("action","/feldolgoz");
                alert(startDate);
             //   $("#dateForm").on("submit",function () { alert("okok"); });
        }*/
}