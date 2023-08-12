import React from 'react';
import '../css/ProductDetailPage.css';

function ProductDetailPage() {

  const product = {
    "type": "book",
    "productId": "64d5f6c16ec48d516225473a",
    "name": "The Girl Who Drank the Moon",
    "description": "THE NO 1 NEW YORK TIMES BESTSELLER AND NEWBERY MEDAL WINNER 'This beautifully written, darkly funny coming-of-age story will enchant and entertain' Daily Mail Every year, the people of the Protectorate leave a baby as an offering to the witch who lives in the forest. They hope this sacrifice will keep her from terrorizing their town. But the witch in the Forest, Xan, is in fact a good witch who shares her home with a wise Swamp Monster and a Perfectly Tiny Dragon. Xan rescues the children and delivers them to welcoming families on the other side of the forest, nourishing the babies with starlight on the journey. One year, Xan accidentally feeds a baby moonlight instead of starlight, filling the ordinary child with extraordinary magic. Xan decides she must raise this girl, whom she calls Luna, as her own. As Luna's thirteenth birthday approaches, her magic begins to emerge - with dangerous consequences. Meanwhile, a young man from the Protectorate is determined to free his people by killing the witch. Deadly birds with uncertain intentions flock nearby. A volcano, quiet for centuries, rumbles just beneath the earth's surface. And the woman with the Tiger's heart is on the prowl . . . The Newbery Medal winner from the author of the highly acclaimed novel The Witch's Boy.",
    "category": "Books",
    "imgMapping": "17",
    "id": 1,
    "price": 29.99,
    "image": "http://0.0.0.0:8080/1.jpg",
    "author": null,
    "specs": {
      "Net Quantity :": "1.00 count",
      "Customer Reviews:": "4.5",
      "Dimensions :": "12.9 x 2.64 x 19.8 cm",
      "Item Weight :": "303 g",
      "Best Sellers Rank:": "#6 in Fiction about Family for Children",
      "Paperback :": "416 pages",
      "Publisher :": "Piccadilly Press (24 August 2017)",
      "ISBN-13 :": "978-1848126473",
      "ISBN-10 :": "1848126476",
      "Country of Origin :": "United Kingdom",
      "Language :": "English",
      "Reading age :": "Customer suggested age: 10 - 12 years"
    }
  };

  if (!product) {
    return <div>Product not found</div>;
  }

  return (
    <div className="product-detail">
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <img src={product.image} alt={product.name} className="product-image" />
          </div>
          <div className="col-md-6">
            <h2 className="product-name">{product.name}</h2>
            <p className="product-category">{product.category}</p>
            <p className="product-description">{product.description}</p>
            <p className="product-price">${product.price.toFixed(2)}</p>
            <div className="product-specs">
              <h4>Specifications:</h4>
              <ul>
                {Object.entries(product.specs).map(([key, value]) => (
                  <li key={key}>
                    <strong>{key}</strong> {value}
                  </li>
                ))}
              </ul>
            </div>
            <button className="btn btn-primary">Add to Cart</button>
          </div>
        </div>
      </div>
    </div>
  );

}

export default ProductDetailPage;
