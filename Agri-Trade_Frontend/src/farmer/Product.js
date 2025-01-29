import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import NavBar from '../components/compo/nav';
import ProductGrid from './ProductGrid';
import { Divider, Stack, Typography, TextField, MenuItem, Select, InputLabel, FormControl, Chip } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
const Product = () => {
  const [productId, setProductId] = useState('');
  const [product, setProduct] = useState(null);
  const [allProducts, setAllProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [token, setToken] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [category, setCategory] = useState('');
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [anchorEl, setAnchorEl] = useState(null);
  useEffect(() => {
    const savedToken = localStorage.getItem('authToken');
    if (savedToken) {
      setToken(savedToken);
      fetchAllProducts(savedToken);
    } else {
      alert('No token found. Please log in first.');
      navigate('/login');
    }
  }, [navigate]);

  const fetchAllProducts = async (token) => {
    try {
      const response = await fetch('http://localhost:5456/farmers/product/all', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      const data = await response.json();
      if (response.ok && data.status === 201) {
        setAllProducts(data.productList || []);
        setFilteredProducts(data.productList || []);
      } else {
        throw new Error(data.message || 'Failed to fetch products');
      }
    } catch (error) {
      console.error('Error fetching all products:', error);
      alert('Failed to fetch products. Please try again.');
    }
  };

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
    setOpen(true);
  };

  // Filter Products Based on Search Query and Category
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

  return (
    <>
      <NavBar />
      <Stack spacing={2} sx={{ px: 3, mt: 2 }}>
        <Typography variant="h4" gutterBottom sx={{ fontWeight: 'bold', textAlign: 'center' }}>
          Products ({filteredProducts.length})
        </Typography>

        <Divider sx={{ height: '3px' }} />

        {/* Search Bar */}
        <Stack direction="row" gap='20px' justifyContent='space-between'>
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
        

        <ProductGrid allProducts={filteredProducts} />
      </Stack>
    </>
  );
};

export default Product;
