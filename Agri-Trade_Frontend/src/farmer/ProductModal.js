import React, { useState, useEffect } from 'react';
import { Modal, Box, Typography, TextField, Button, MenuItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import { Stack } from 'react-bootstrap';
import Autocomplete from "@mui/material/Autocomplete";
import axios from "axios";
const ProductModal = ({ open, onClose, productData }) => {
  console.log(productData);
  const [productName, setProductName] = useState('');
  const [productDescription, setProductDescription] = useState('');
  const [category, setCategory] = useState('');
  const [productImg, setProductImg] = useState(null);
  const [productStock, setProductStock] = useState('');
  const [productQuantity, setProductQuantity] = useState('');
  const [productPrice, setProductPrice] = useState('');
  const [listingDate, setListingDate] = useState('');
  const [prodId , setProdId] = useState('');
   const [token, setToken] = useState(null);
   const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const getImageUrl = (imageName) => {
    // console.log(imageName);
    const image = `http://localhost:5456${imageName}`;
    // console.log(image);
    return image;
  };

   useEffect(() => {
                const savedToken = localStorage.getItem('authToken');
                if (savedToken) {
                    setToken(savedToken);
                } else {
                    alert('No token found. Please log in first.');
                    navigate('/login');
                }
            }, [navigate]);

  useEffect(() => {
    if (productData) {
      setProductName(productData.prod_Name || '');
      setProductDescription(productData.prod_Description || '');
      setCategory(productData.category || '');
      setProductStock(productData.prod_Stock || '');
      setProductQuantity(productData.prod_Quantity || '');
      setProductPrice(productData.prod_Price || '');
      setListingDate(productData.listing_Date || '');
      setProductImg(getImageUrl(productData.prod_Img)|| null);
      setProdId(productData.prod_id || '');
    }
  }, [productData]);

  const handleImageUpload = (e) => {
    setProductImg(e.target.files[0]);
  };

  const handleFormSubmit = async(e) => {
    e.preventDefault();

    const productData = {
      prod_Name: productName,
      prod_Description: productDescription,
      category,
      prod_Stock: productStock,
      prod_Quantity: productQuantity,
      prod_Price: productPrice,
      listing_Date: listingDate,
  };

  try {
    const formData = new FormData();
    formData.append('id', prodId);
    formData.append('products', JSON.stringify(productData));

    // If you have an image to upload:
    if (productData.prodImage) {
        formData.append('prodImage', productData.prodImage, productData.prodImage.name);
    }

    const response = await fetch(`http://localhost:5456/farmers/product?id=${prodId}`, {
        method: 'PUT',
        headers: {
            Authorization: `Bearer ${token}`,
        },
        body: formData,
    });

    const responseData = await response.json();

    if (!response.ok) {
        throw new Error(responseData.message || 'Failed to edit product');
    }

    window.location.reload();
} catch (error) {
    console.error('Error:', error);
    alert('Failed to edit Product. Please try again.');
}
    
  };

 const [dropdownOptions, setDropdownOptions] = useState({
     states: [],
     districts: [],
     markets: [],
     commodities: [],
   });
 
   const options = [
     'FRESH',
     'ORGANIC',
   ];
 
   const renderDropdown = (label, key, options) => (
       <Box mb={2} width="100%">
         <Autocomplete
           options={options}
           getOptionLabel={(option) => option}
           renderInput={(params) => <TextField {...params} label="Product Name" variant="outlined" />}
           onChange={(event, newValue) =>
             setProductName(newValue)
           }
           value={productName || ""}
           loading={loading}
           loadingText="Loading options..."
           ListboxProps={{ style: { maxHeight: 200, overflow: "auto" } }} // Makes it scrollable
         />
       </Box>
     );

     useEffect(() => {
      const fetchDropdownData = async () => {
        setLoading(true);
        try {
          const response = await axios.get("http://127.0.0.1:5000/fetch_data");
          setDropdownOptions(response.data);
        } catch (error) {
          console.error("Error fetching dropdown data:", error);
        } finally {
          setLoading(false);
        }
      };
      fetchDropdownData();
    }, []);
  

  return (
    
    <Modal open={open} onClose={onClose}>
      <Box
  sx={{
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    borderRadius: 2,
    boxShadow: 24,
    p: 4,
  }}
>
  {/* Stack for Header */}
  <Stack direction='row'>
    <Typography variant="h6">
      Edit Product
    </Typography>
    <HelpOutlineIcon sx={{ cursor: 'pointer', color: 'gray' }} />
  </Stack>

  {/* Form */}
  <form onSubmit={handleFormSubmit}>
  {renderDropdown("Commodity", "commodity", dropdownOptions.commodities)}
    
    <TextField
      fullWidth
      label="Product Description"
      variant="outlined"
      value={productDescription}
      onChange={(e) => setProductDescription(e.target.value)}
      sx={{ mb: 2 }}
      required
    />
   
   <TextField
              fullWidth
              label="Category"
              variant="outlined"
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              sx={{ mb: 2 }}
              required
              select
            >
            {options.map((option) => (
          <MenuItem key={option} value={option}>
            {option}
          </MenuItem>
        ))}
        </TextField>
    <TextField
      fullWidth
      type="number"
      label="Stock"
      variant="outlined"
      value={productStock}
      onChange={(e) => setProductStock(e.target.value)}
      sx={{ mb: 2 }}
      required
    />
    <TextField
      fullWidth
      type="number"
      label="Quantity"
      variant="outlined"
      value={productQuantity}
      onChange={(e) => setProductQuantity(e.target.value)}
      sx={{ mb: 2 }}
      required
    />
    <TextField
      fullWidth
      type="number"
      label="Price"
      variant="outlined"
      value={productPrice}
      onChange={(e) => setProductPrice(e.target.value)}
      sx={{ mb: 2 }}
      required
    />
    <TextField
      fullWidth
      type="date"
      label="Listing Date"
      variant="outlined"
      value={listingDate}
      onChange={(e) => setListingDate(e.target.value)}
      InputLabelProps={{ shrink: true }}
      sx={{ mb: 2 }}
      required
    />
    <Button type="submit" variant="contained" color="primary" fullWidth>
      {productData ? 'Update Product' : 'Add Product'}
    </Button>
  </form>
</Box>

    </Modal>
  );
};

export default ProductModal;
