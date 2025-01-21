import React, { useState } from "react";
import {
  Container,
  Typography,
  Button,
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import { styled } from "@mui/material/styles";
import Papa from "papaparse";
import { Navbar } from "react-bootstrap";

// Custom Styled Components
const UploadButton = styled(Button)({
  marginTop: 20,
  marginBottom: 20,
  fontSize: "16px",
  fontWeight: "bold",
  background: "linear-gradient(45deg, #1e88e5, #42a5f5)",
  color: "#ffffff",
  padding: "10px 20px",
  "&:hover": {
    background: "linear-gradient(45deg, #1565c0, #1e88e5)",
  },
});

const GradientHeaderCell = styled(TableCell)({
  background: "linear-gradient(45deg, #f48fb1, #f06292)",
  color: "#ffffff",
  fontWeight: "bold",
  fontSize: "16px",
  textAlign: "center",
});

const DummyGraph = styled(Box)({
  height: 200,
  margin: 20,
  background: "linear-gradient(45deg, #6a1b9a, #ab47bc)",
  borderRadius: 8,
});

const Title = styled(Typography)({
  fontFamily: "'Roboto Slab', serif",
  color: "#2e7d32",
  textAlign: "center",
  fontWeight: "bold",
  marginBottom: 30,
});

const Insight = () => {
  const [tableData, setTableData] = useState([]);
  const [headers, setHeaders] = useState([]);

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    Papa.parse(file, {
      header: true,
      complete: (results) => {
        const rows = results.data.slice(0, 5); // Show only the top 5 rows
        const headerKeys = Object.keys(results.data[0]);
        setHeaders(headerKeys);
        setTableData(rows);
      },
    });
  };

  return (
   
    <Container maxWidth="md">
      <Title variant="h4">Exploratory Data Analysis (EDA)</Title>

      <UploadButton variant="contained" component="label">
        Add Dataset
        <input
          type="file"
          hidden
          accept=".csv"
          onChange={handleFileUpload}
        />
      </UploadButton>

      {tableData.length > 0 && (
        <>
          <Typography variant="h6" gutterBottom>
            Top 5 Rows of Uploaded Dataset
          </Typography>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  {headers.map((header) => (
                    <GradientHeaderCell key={header}>
                      {header}
                    </GradientHeaderCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {tableData.map((row, index) => (
                  <TableRow key={index}>
                    {headers.map((header) => (
                      <TableCell key={header}>{row[header]}</TableCell>
                    ))}
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </>
      )}

      <Box mt={4}>
        <Typography variant="h5" gutterBottom>
          Report
        </Typography>
        <h6>1.Correlation between Numeric Attributes</h6>
        <img
          src="/1.png"
          alt="Graph 1"
          style={{ width: "100%", height: "100%", borderRadius: "8px", margin: "10px 0" }}
        />
        <h6>2.Analyzing Target Variable</h6>
        <img
          src="/2.png"
          alt="Graph 2"
          style={{ width: "100%", height: "100%", borderRadius: "8px", margin: "10px 0" }}
        />
        <h6>3.Data Distribution</h6>
        <img
          src="/3.png"
          alt="Graph 3"
          style={{ width: "100%", height: "100%", borderRadius: "8px", margin: "10px 0" }}
        />
        <h6>4.Checking for Outliers</h6>
        <img
          src="/4.png"
          alt="Graph 4"
          style={{ width: "100%", height: "100%", borderRadius: "8px", margin: "10px 0" }}
        />
      </Box>
    </Container>
  );
};

export default Insight;
