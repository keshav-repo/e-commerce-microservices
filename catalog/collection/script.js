var links =[];
for(i=0; i<arr.length; i++){
    var l = "https://www.myntra.com"+arr[i].getAttribute('href');
    links.push({
        "name": arr[i].text,
        "url": l
    });
}


var links =[];
var con = document.getElementsByClassName("desktop-categoryContainer");
for( i=0; i<con.length; i++){
    var ele = con[i].getElementsByClassName('desktop-categoryName');
    for(j=0; j<ele.length; j++){
        var l = "https://www.myntra.com"+ele[j].getAttribute('href');
        links.push({
            "name": ele[j].text,
            "url": l
        });
    }
}

db.productCard.aggregate([
    { $unwind: "$tags" },
    { $group: { _id: null, distinctTags: { $addToSet: "$tags" } } }
  ]);

  
