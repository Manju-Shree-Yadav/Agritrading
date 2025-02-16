import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavBar from '../components/compo/nav';
import '../styles/global.css';
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap CSS
import { Spinner, Container, Row, Col, Card, Button, Modal, Form } from 'react-bootstrap'; // Import Bootstrap components
import { Divider, Stack, Typography, TextField, MenuItem, Select, InputLabel, FormControl, Chip } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const ConsumerHome = () => {
    const [token, setToken] = useState(null);
    const [allProducts, setAllProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [userName, setUserName] = useState('');
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [showFeedbackModal, setShowFeedbackModal] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [feedbacks, setFeedbacks] = useState([]);
    const [category, setCategory] = useState('');
    const [quantity, setQuantity] = useState(1);
    const navigate = useNavigate();
    const [open, setOpen] = useState(false);
    const [anchorEl, setAnchorEl] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
      const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
        setOpen(true);
      };
    useEffect(() => {
        const savedAuth = JSON.parse(localStorage.getItem('authData'));
        const savedToken = localStorage.getItem('authToken');
        if (savedAuth && savedAuth.token) {
            setToken(savedToken);
            setUserName(savedAuth.username);
            fetchAllProducts(savedAuth.token);
        } else {
            alert('Hey consumer, please log in first.');
            navigate('/login');
        }
    }, [navigate]);


    useEffect(() => {
        let filtered = allProducts;
        
        if (searchQuery) {
          filtered = filtered.filter(product => 
            product.prod_Name.toLowerCase().includes(searchQuery.toLowerCase())
          );
        }
    
        if (category) {
          filtered = filtered.filter(product => product.category === category);
        }
    
        setFilteredProducts(filtered);
      }, [searchQuery, category, allProducts]);
    

    const fetchAllProducts = async (token) => {
        try {
            const response = await fetch('http://localhost:5456/customer/products', {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await response.json();
            if (response.ok) {
                setAllProducts(data.productList || []);
                setFilteredProducts(data.productList || []);
            } else {
                throw new Error(data.message || 'Failed to fetch products');
            }
        } catch (error) {
            console.error('Error fetching all products:', error);
            alert('Unable to fetch products at the moment. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    const handleBuyClick = (product) => {
        setSelectedProduct(product);
        setShowModal(true);
    };

    const handleFeedbackClick = async (product) => {
        setSelectedProduct(product);
        try {
            const response = await fetch(`http://localhost:5456/farmer/getfeedback?id=${product.prod_id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            const data = await response.json();
            if (response.ok) {
                setFeedbacks(data.feedbackList || []);
            } else {
                setFeedbacks([]);
            }
        } catch (error) {
            console.error('Error fetching feedback:', error);
            setFeedbacks([]);
        } finally {
            setShowFeedbackModal(true);
        }
    };
    
    const getImageUrl = (imageName) => {
    
        const urlimg =  `http://localhost:5456${imageName}`;
        console.log(urlimg);
        return urlimg;
      };
    

    const handleOrderSubmit = async () => {
        if (!selectedProduct) return;
        try {
            const response = await fetch('http://localhost:5456/customers/orders', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify({
                    productId: selectedProduct.prod_id,
                    quantity,
                }),
            });
            const data = await response.json();
            if (response.ok) {
                alert('Order placed successfully');
                setShowModal(false);
            } else {
                throw new Error(data.message || 'Failed to place order');
            }
        } catch (error) {
            console.error('Error placing order:', error);
            alert('Unable to place order at the moment. Please try again later.');
        }
    };

    return (
        <div>
            <NavBar />
            <Container className="mt-4">
                <h2 className='text-center'>Hey {userName}! Welcome</h2>
                <h5 className='text-center mb-4'>Please make yourself comfortable in finding our esteemed products</h5>
                
                {/* Changes Made */}
                <Stack direction="row" gap='20px' justifyContent='space-between' marginBottom='30px'>
          <Stack direction='row' marginLeft='60px' alignItems='end' gap='10px'>
            <SearchIcon></SearchIcon>
          <TextField
          label="Search by Product"
          size="small"
          variant="standard"
          fullWidth
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />

          </Stack>

          <Stack direction="row" marginRight="60px" alignItems="end" gap="10px">
      <Chip
        label={`FILTER: ${category || 'ALL'}`}
        style={{ backgroundColor: 'lightgrey', cursor: 'pointer' }}
        onClick={handleClick}
      />
      <Select
        value={category}
        onChange={(e) => {
          setCategory(e.target.value);
          setOpen(false);
        }}
        open={open}
        onClose={() => setOpen(false)}
        MenuProps={{
          anchorEl: anchorEl,
          open: open,
          onClose: () => setOpen(false),
          PaperProps: {
            style: {
              marginTop: 8, // Adjusts spacing between chip and dropdown
            },
          },
        }}
        sx={{ display: 'none' }} // Hide default select UI
      >
        <MenuItem value="">ALL</MenuItem>
        <MenuItem value="FRESH">FRESH</MenuItem>
        <MenuItem value="ORGANIC">ORGANIC</MenuItem>
      </Select>
    </Stack>
       
        {/* Category Filter */}
        {/* <FormControl sx={{width: '200px' , marginRight: '60px'}} >
          <InputLabel>Filter by Category</InputLabel>
          <Select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
          >
            <MenuItem value="">All</MenuItem>
            <MenuItem value="FRESH">FRESH</MenuItem>
            <MenuItem value="ORGANIC">ORGANIC</MenuItem>
          </Select>
        </FormControl> */}
        </Stack>
                
                {loading ? (
                    <div className="d-flex justify-content-center">
                        <Spinner animation="border" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </Spinner>
                    </div>
                ) : (
                    <Row>
                        {filteredProducts.length > 0 ? (
                            filteredProducts.map((product, index) => (
                                <Col key={product.prod_id || index} md={4} className="mb-4">
                                    <Card style={{ height: "700px", overflow: "hidden" }}>
                                    <Card.Img 
    variant="top" 
    src={getImageUrl(product.prod_Img)} 
    alt={product.prod_Name} 
    style={{
        height: "400px",      // Fixed height for consistency
        width: "100%",        // Ensures it spans full card width
        objectFit: "cover",   // Crops image while maintaining aspect ratio
        borderRadius: "8px"   // Optional: Adds a smoother UI
    }} 
/>

    <Card.Body>
        <Card.Title>{product.prod_Name}</Card.Title>
        <Card.Text>{product.prod_Description}</Card.Text>
        <Card.Text>Price: ₹{product.prod_Price}</Card.Text>
        <Card.Text>Stock: {product.prod_Stock}</Card.Text>
        {product.prod_Stock> 0 ?  (<>
            <Button variant="primary" onClick={() => handleBuyClick(product)}>Buy</Button>
            <Button variant="secondary" onClick={() => handleFeedbackClick(product)} className="ms-2">Feedback</Button>
        </>):(<>
            <Button variant="primary" disabled onClick={() => handleBuyClick(product)}>Buy</Button>
            <Button variant="secondary" onClick={() => handleFeedbackClick(product)} className="ms-2">Feedback</Button>
        </>)}
    </Card.Body>
</Card>

                                </Col>
                            ))
                        ) : (
                            <p className="text-center">No products available at the moment.</p>
                        )}
                    </Row>
                )}
            </Container>

            

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Place Order</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="productId">
                            <Form.Label>Product ID</Form.Label>
                            <Form.Control type="text" value={selectedProduct?.prod_id || ''} readOnly />
                        </Form.Group>
                        <Form.Group controlId="quantity" className="mt-3">
                            <Form.Label>Quantity</Form.Label>
                            <Form.Control type="number" value={quantity} onChange={(e) => setQuantity(e.target.value)} min="1" />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>Close</Button>
                    <Button variant="primary" onClick={handleOrderSubmit}>Place Order</Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showFeedbackModal} onHide={() => setShowFeedbackModal(false)}>
    <Modal.Header closeButton>
        <Modal.Title>Product Feedback</Modal.Title>
    </Modal.Header>
    <Modal.Body>
        {feedbacks.length > 0 ? (
            feedbacks.map((feedback, index) => (
                <div key={index} className="mb-3">
                    <p><strong>{feedback.customer.name}</strong> ({feedback.customer.contactInfo})</p>
                    <p>Rating: {feedback.rating}/5</p>
                    <p><strong>Review:</strong> {feedback.description}</p>
                    <hr />
                </div>
            ))
        ) : (
            <p>No feedback available for this product.</p>
        )}
    </Modal.Body>
    <Modal.Footer>
        <Button variant="secondary" onClick={() => setShowFeedbackModal(false)}>Close</Button>
    </Modal.Footer>
</Modal>

        </div>
    );
};

export default ConsumerHome;