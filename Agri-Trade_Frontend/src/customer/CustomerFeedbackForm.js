import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Form, Button, Alert, Card, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaStar } from 'react-icons/fa';
import './CustomerFeedbackForm.css';

const CustomerFeedbackForm = () => {
  const [token, setToken] = useState(null);
  const [profileData, setProfileData] = useState(null);
  const [feedback, setFeedback] = useState('');
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(null);
  const [feedbackList, setFeedbackList] = useState([]);
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState('');
  const navigate = useNavigate();
  const getImageUrl = (imageName) => {
    
    const urlimg =  `http://localhost:5456${imageName}`;
    console.log(urlimg);
    return urlimg;
  };
  useEffect(() => {
    const savedAuth = JSON.parse(localStorage.getItem('authData'));
    if (savedAuth && savedAuth.token) {
      setToken(savedAuth.token);
      fetchProfile(savedAuth.token);
      fetchProducts(savedAuth.token);
      fetchFeedback(savedAuth.token);
      console.log('Token found:', savedAuth.token);
    } else {
      alert('No token found. Please log in first.');
      navigate('/login');
    }
  }, [navigate]);

  const fetchProfile = async (token) => {
    try {
      const response = await fetch('http://localhost:5456/profile/customer', {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const data = await response.json();
      console.log('Fetched profile data:', data);

      if (response.ok) {
        setProfileData(data.customer);
        console.log('Customer ID:', data.customer.customerId);
      } else {
        throw new Error(data.message || 'Failed to fetch profile');
      }
    } catch (error) {
      console.error('Error fetching profile:', error);
      alert('Failed to fetch profile. Please try again.');
    }
  };

  const fetchProducts = async (token) => {
    try {
      const response = await fetch('http://localhost:5456/customer/products', {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const data = await response.json();

      if (response.ok) {
        const productList = data.productList.map(product => ({
          id: product.prod_id,
          name: product.prod_Name
        }));
        setProducts(productList);
      } else {
        throw new Error(data.message || 'Failed to fetch products');
      }
    } catch (error) {
      console.error('Error fetching products:', error);
      alert('Failed to fetch products. Please try again.');
    }
  };

  const fetchFeedback = async (token) => {
    try {
      const response = await fetch('http://localhost:5456/customer/getfeedbacks', {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const data = await response.json();
      console.log('Fetched feedback data:', data);

      if (response.ok) {
        setFeedbackList(data.feedbackList);
      } else {
        throw new Error(data.message || 'Failed to fetch feedback');
      }
    } catch (error) {
      console.error('Error fetching feedback:', error);
      alert('Failed to fetch feedback. Please try again.');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Submitting feedback for customer ID:', profileData.customerId);
    console.log('Profile Data:', profileData);
    try {
      const response = await fetch('http://localhost:5456/addfeedback', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          productId: selectedProduct,
          customerId: profileData.customerId,
          customerPhone: profileData.contactInfo,
          rating,
          description: feedback,
        }),
      });

      const data = await response.json();

      if (response.ok) {
        setFeedbackList((prev) => [...prev, data.feedback]);
        setFeedback('');
        setRating(0);
        setSelectedProduct('');
        alert('Feedback submitted successfully!');
      } else {
        throw new Error(data.message || 'Failed to submit feedback');
      }
    } catch (error) {
      console.error('Error submitting feedback:', error);
      alert('Failed to submit feedback. Please try again.');
    }
  };

  const renderStars = (rating) => {
    return [...Array(5)].map((_, index) => (
      <FaStar
        key={index}
        size={20}
        color={index < rating ? '#ffc107' : '#e4e5e9'}
      />
    ));
  };

  return (
    <Container className="my-5">
      <Row className="justify-content-center">
        <Col md={8}>
          <Card className="shadow-lg border-0 rounded-3 animate__animated animate__fadeIn">
            <Card.Body className="p-5">
              <h4 className="text-center mb-4 text-primary fw-bold">We Value Your Feedback!</h4>
              <Form onSubmit={handleSubmit}>
                <Form.Group controlId="customerName" className="mb-4">
                  <Form.Label className="fw-semibold">Your Name</Form.Label>
                  <Form.Control
                    type="text"
                    value={profileData?.name || ''}
                    readOnly
                    className="rounded-pill bg-light"
                  />
                </Form.Group>
                <Form.Group controlId="phoneNumber" className="mb-4">
                  <Form.Label className="fw-semibold">Phone Number</Form.Label>
                  <Form.Control
                    type="text"
                    value={profileData?.contactInfo || ''}
                    readOnly
                    className="rounded-pill bg-light"
                  />
                </Form.Group>
                <Form.Group controlId="product" className="mb-4">
                  <Form.Label className="fw-semibold">Select Product</Form.Label>
                  <Form.Control
                    as="select"
                    value={selectedProduct}
                    onChange={(e) => setSelectedProduct(e.target.value)}
                    className="rounded-pill"
                  >
                    <option value="">Choose a Product</option>
                    {products.map((product) => (
                      <option key={product.id} value={product.id}>
                        {product.name}
                      </option>
                    ))}
                  </Form.Control>
                </Form.Group>
                <Form.Group controlId="rating" className="mb-4">
                  <Form.Label className="fw-semibold">Rate Your Experience</Form.Label>
                  <div className="star-rating">
                    {[...Array(5)].map((star, index) => {
                      const ratingValue = index + 1;
                      return (
                        <label key={index}>
                          <input
                            type="radio"
                            name="rating"
                            value={ratingValue}
                            onClick={() => setRating(ratingValue)}
                            style={{ display: 'none' }}
                          />
                          <FaStar
                            size={35}
                            className="star"
                            color={ratingValue <= (hover || rating) ? '#ffc107' : '#e4e5e9'}
                            onMouseEnter={() => setHover(ratingValue)}
                            onMouseLeave={() => setHover(null)}
                          />
                        </label>
                      );
                    })}
                  </div>
                </Form.Group>
                <Form.Group controlId="feedback" className="mb-4">
                  <Form.Label className="fw-semibold">Your Feedback</Form.Label>
                  <Form.Control
                    as="textarea"
                    rows={4}
                    value={feedback}
                    onChange={(e) => setFeedback(e.target.value)}
                    placeholder="Tell us about your experience..."
                    className="rounded-3"
                  />
                </Form.Group>
                <Button
                  variant="primary"
                  type="submit"
                  className="w-100 rounded-pill py-2 fw-semibold shadow-sm"
                >
                  Submit Feedback
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <Row className="justify-content-center mt-5">
        <Col md={10}>
          <h4 className="text-center mb-4 text-primary fw-bold">Customer Reviews</h4>
          {feedbackList.length > 0 ? (
            feedbackList.map((fb, index) => (
              <Card key={index} className="mb-4 shadow-sm border-0 rounded-3 animate__animated animate__fadeInUp">
                <Card.Body className="d-flex">
                  <div className="me-4">
                    <img
                      src={getImageUrl(fb.product.prod_Img)} 
                      alt={fb.product.prod_Name}
                      className="rounded-circle feedback-img"
                      onError={(e) => (e.target.src = 'https://via.placeholder.com/100')}
                    />
                  </div>
                  <div className="flex-grow-1">
                    <Card.Title className="text-primary">
                      {fb.product.prod_Name}{' '}
                      <Badge bg="secondary" className="ms-2">{fb.product.category}</Badge>
                    </Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">
                      <span className="fw-semibold">Customer:</span> {fb.customer.name} |{' '}
                      <span className="fw-semibold">Rating:</span> {renderStars(fb.rating)}
                    </Card.Subtitle>
                    <Card.Text className="text-dark mt-2">"{fb.description}"</Card.Text>
                    <Card.Text className="text-muted small">
                      <span className="fw-semibold">Farmer:</span> {fb.product.farmerDTO.name} ({fb.product.farmerDTO.farmLocation}) |{' '}
                      <span className="text-success">{fb.product.farmerDTO.farmType}</span>
                    </Card.Text>
                    <Card.Text className="text-muted small">
                      <span className="fw-semibold">Price:</span> ₹{fb.product.prod_Price} |{' '}
                      <span className="fw-semibold">Stock:</span> {fb.product.prod_Stock}
                    </Card.Text>
                  </div>
                </Card.Body>
              </Card>
            ))
          ) : (
            <Alert variant="info" className="text-center rounded-pill">
              No feedback available yet. Be the first to share!
            </Alert>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default CustomerFeedbackForm;