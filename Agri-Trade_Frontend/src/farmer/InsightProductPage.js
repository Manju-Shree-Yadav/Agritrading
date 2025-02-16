import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Container,
  Paper,
  Typography,
  CircularProgress,
  TextField,
} from "@mui/material";
import Autocomplete from "@mui/material/Autocomplete";
import axios from "axios";
import NavBar from "../components/compo/nav";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, ReferenceLine } from "recharts";

const InsightPage = () => {
  
  const [loading, setLoading] = useState(false);
  const [dropdownOptions, setDropdownOptions] = useState({
    states: [],
    districts: [],
    markets: [],
    commodities: [],
  });
  const [selectedValue, setSelectedValue] = useState({
    state: "",
    district: "",
    market: "",
    commodity: "",
    month: 6,
  });
  const [graphData, setGraphData] = useState([]);
  const [priceData, setPriceData] = useState(null);
  const maxPrice = graphData.length > 0 ? Math.max(...graphData.map((item) => item.price)) : 0;
  const minPrice = graphData.length > 0 ? Math.min(...graphData.map((item) => item.price)) : 0;


  const fetchGraphData = async () => {
    const { commodity, month } = selectedValue;
    try {
      const response = await axios.get("http://127.0.0.1:5000/get_graph_data", {
        params: { commodity, month },
      });
      setGraphData(response.data);
    } catch (error) {
      console.error("Error fetching graph data:", error);
    }
  };

  useEffect(() => {
    if (selectedValue.commodity && selectedValue.month) {
      fetchGraphData();
    }
  }, [selectedValue]);
  // Fetch dropdown options dynamically
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

  // const fetchGraph = async () => {
  //   const { commodity, month } = selectedValue;
  //   try {
  //     const response = await axios.get("http://127.0.0.1:5000/generate_graph", {
  //       params: { commodity, month },
  //       responseType: "arraybuffer",
  //     });
  //     const graphImage = `data:image/png;base64,${btoa(
  //       String.fromCharCode(...new Uint8Array(response.data))
  //     )}`;
  //     setGraphData(graphImage);
  //   } catch (error) {
  //     console.error("Error fetching graph:", error);
  //   }
  // };

  const fetchPrice = async () => {
    try {
      const response = await axios.post("http://127.0.0.1:5000/recommend_price", {
        state: selectedValue.state,
        district: selectedValue.district,
        market: selectedValue.market,
        commodity: selectedValue.commodity,
        month: selectedValue.month,
      });
      setPriceData(response.data);
    } catch (error) {
      console.error("Error fetching price:", error);
    }
  };

  const handleSubmit = () => {
    const { commodity, month, state, district, market } = selectedValue;

    if (commodity && month) {
      // fetchGraphData();
    }

    if (state && district && market && commodity && month) {
      fetchPrice();
    }
  };

  const renderDropdown = (label, key, options) => (
    <Box mb={2} width="100%">
      <Autocomplete
        options={options}
        getOptionLabel={(option) => option}
        renderInput={(params) => <TextField {...params} label={label} variant="outlined" />}
        onChange={(event, newValue) =>
          setSelectedValue((prev) => ({ ...prev, [key]: newValue || "" }))
        }
        value={selectedValue[key] || ""}
        loading={loading}
        loadingText="Loading options..."
        ListboxProps={{ style: { maxHeight: 200, overflow: "auto" } }} // Makes it scrollable
      />
    </Box>
  );

  return (
    <Box
      sx={{
        minHeight: "100vh",
        // backgroundImage: `linear-gradient(to right, #ff7e5f, #feb47b)`,
        paddingBottom: 4,
      }}
    >
      <NavBar />
      
      <Container maxWidth="md">
        {/* Page Title */}
        <Typography
          variant="h4"
          align="center"
          gutterBottom
          sx={{
            fontWeight: 600,
            color: "#333",
            textShadow: "1px 1px 5px rgba(0,0,0,0.1)",
            marginTop: 3,
          }}
        >
          📊 Market Insight
        </Typography>

        {/* Graph Section */}
        <Paper
          elevation={5}
          sx={{
            borderRadius: 3,
            p: 3,
            textAlign: "center",
            background: "linear-gradient(135deg, #fdfbfb, #ebedee)",
            boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
          }}
        >
          <Typography variant="h6" sx={{ fontWeight: 500, marginBottom: 2 }}>
            Price Trends for {selectedValue.commodity || "Selected Commodity"}
          </Typography>

          {graphData.length > 0 && (
            <Typography variant="body1" sx={{ fontSize: "1rem", fontWeight: 500, color: "#555" }}>
              Max Price: <span style={{ color: "green", fontWeight: "bold" }}>₹{maxPrice}</span> | 
              Min Price: <span style={{ color: "red", fontWeight: "bold" }}>₹{minPrice}</span>
            </Typography>
          )}

          {graphData.length > 0 ? (
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={graphData}>
                <CartesianGrid strokeDasharray="3 3" strokeOpacity={0.3} />
                <XAxis dataKey="price" tick={{ fontSize: 12, fill: "#333" }} />
                <YAxis />
                <Tooltip />
                
                {/* Green Line for Max Price */}
                <ReferenceLine y={maxPrice} label="Max" stroke="green" strokeWidth={2} strokeDasharray="4 4" />

                {/* Red Line for Min Price */}
                <ReferenceLine y={minPrice} label="Min" stroke="red" strokeWidth={2} strokeDasharray="4 4" />

                <Line type="monotone" dataKey="price" stroke="#8884d8" strokeWidth={2} />
              </LineChart>
            </ResponsiveContainer>
          ) : (
            <Typography variant="body1" sx={{ color: "#777", marginTop: 2 }}>
              Select options and submit to generate the graph.
            </Typography>
          )}
        </Paper>

        {/* Dropdowns */}
        <Box mt={4} display="flex" flexDirection="column" gap={2}>
          {renderDropdown("State", "state", dropdownOptions.states)}
          {renderDropdown("District", "district", dropdownOptions.districts)}
          {renderDropdown("Market", "market", dropdownOptions.markets)}
          {renderDropdown("Commodity", "commodity", dropdownOptions.commodities)}
        </Box>

        {/* Submit Button */}
        <Box display="flex" justifyContent="center" mt={4}>
          <Button
            variant="contained"
            sx={{
              background: "linear-gradient(135deg, #3b82f6, #2563eb)",
              color: "white",
              fontWeight: "bold",
              px: 4,
              py: 1.5,
              borderRadius: "30px",
              "&:hover": { background: "linear-gradient(135deg, #2563eb, #1d4ed8)" },
            }}
            onClick={handleSubmit}
            disabled={loading}
            startIcon={loading ? <CircularProgress size={20} color="inherit" /> : null}
          >
            Submit
          </Button>
        </Box>

        {/* Prediction Section */}
        {priceData && (
          <Paper
            elevation={4}
            sx={{
              mt: 4,
              p: 3,
              borderRadius: 3,
              textAlign: "center",
              background: "linear-gradient(135deg, #fdfbfb, #ebedee)",
              boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
            }}
          >
            <Typography variant="h6" sx={{ fontWeight: 600 }}>🔮 Price Prediction</Typography>
            <Typography variant="body1" sx={{ fontWeight: 500 }}>Max Price: ₹{priceData.max_price}</Typography>
            <Typography variant="body1" sx={{ fontWeight: 500 }}>Min Price: ₹{priceData.min_price}</Typography>
            <Typography variant="body1" sx={{ fontWeight: 500, color: "#2563eb" }}>
              Predicted Price: ₹{priceData.predicted_price.toFixed(2)}
            </Typography>
          </Paper>
        )}
      </Container>
    </Box>
  );
};

export default InsightPage;
