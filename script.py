import psycopg2
import json
from datetime import datetime

# Database credentials
DB_NAME = "vaysolar"
DB_HOST = "vaysolardev1.cvxgiqqpcwib.ap-south-1.rds.amazonaws.com"
DB_PORT = 5432
DB_USER = "postgres"
DB_PASSWORD = "QwWgkvGzoAqsjVbbNb8Z"

# Connect to the PostgreSQL database
try:
    conn = psycopg2.connect(
        dbname=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD,
        host=DB_HOST,
        port=DB_PORT
    )
    cursor = conn.cursor()
    print("Connected to the database successfully!")
except Exception as e:
    print("Failed to connect to the database!")
    print(e)
    exit()

fetch_query = """
    SELECT 
        MAX(pc.id) AS id,
        pc.config,
        pc.plant_size,
        pc.plant_type,
        pc.panel_type,
        pc.inverter_brand,
        pc.panel_brand,
        pc.battery_brand,
        pc.net_cost,
        pc.metadata,
        MAX(pc.created_at) AS created_at,
        MAX(pc.updated_at) AS updated_at,
        pc.vendor_id,
        CASE
            WHEN COUNT(*) > 1 THEN 'STATE'
            ELSE 'DISTRICT'
        END AS region_type,
        CASE
            WHEN COUNT(*) > 1 THEN MAX(pc.state)
            ELSE MAX(pc.district)
        END AS region
    FROM 
        dbo.proposal_configs pc
    GROUP BY 
        pc.config, 
        pc.plant_size, 
        pc.plant_type, 
        pc.panel_type, 
        pc.inverter_brand, 
        pc.panel_brand, 
        pc.battery_brand, 
        pc.net_cost, 
        pc.metadata, 
        pc.vendor_id, 
        pc.state;
"""






insert_query = """
    INSERT INTO dbo.proposal_config_base (
        config, plant_size, plant_type, panel_type, inverter_brand, 
        panel_brand, battery_brand, net_cost, metadata, created_at, 
        updated_at, vendor_id
    ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) RETURNING id;
"""


region_insert_query = """
    INSERT INTO dbo.proposal_config_regions_mapping (
        config_id, region_type, region, metadata, created_at, updated_at
    ) VALUES (%s, %s, %s, %s, %s, %s);
"""

try:
    cursor.execute(fetch_query)
    rows = cursor.fetchall()
    
    for row in rows:
        (
            id, config, plant_size, plant_type, panel_type, inverter_brand, 
            panel_brand, battery_brand, net_cost, metadata, created_at, 
            updated_at, vendor_id, region_type, region
        ) = row
        
      
        config_str = json.dumps(config) if isinstance(config, dict) else config
        metadata_str = json.dumps(metadata) if isinstance(metadata, dict) else metadata

        
        # cursor.execute(insert_query, (
        #     id, config_str, plant_size, plant_type, panel_type, inverter_brand, 
        #     panel_brand, battery_brand, net_cost, metadata_str, created_at, 
        #     updated_at, vendor_id
        # ))

        cursor.execute(insert_query, (
            config_str, plant_size, plant_type, panel_type, inverter_brand, 
            panel_brand, battery_brand, net_cost, metadata_str, created_at, 
            updated_at, vendor_id
        ))
        new_id = cursor.fetchone()[0]  # Get the new ID
        region_metadata = "{}"  
        current_timestamp = datetime.now()
        cursor.execute(region_insert_query, (
             new_id, region_type, region, region_metadata, current_timestamp, current_timestamp
        ))
        
      
       
        
        # cursor.execute(region_insert_query, (
        #     id, region_type, region, region_metadata, current_timestamp, current_timestamp
        # ))
    
   
    conn.commit()
    print("Data inserted and backfilled successfully!")
except Exception as e:
    print("Failed to insert or backfill data!")
    print(e)
    conn.rollback()


cursor.close()
conn.close()
