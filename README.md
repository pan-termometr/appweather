# Appweather

AppWeather is an application used to get historical data for the last 7 days based on the given coordinates. It uses an external API that provides precipitation data with a delay (~7 days) hence for most coordinates average precipitation is 0.0 or no information.


### DB config
1. Run "docker-compose up" in terminal to mount postgres db docker container.
2. Go to localhost:5050 in your browser. 
   * Login: admin@admin.com
   * Pass: admin
3. Click Add new server: 
   * General Tab:
     * Name: appweather
   * Connection:
     * Hostname/address: db_container
     * Port: 5432
     * Maintenance database: postgres
     * Username: root
     * Password: root

### Sample request

localhost:8081/api/v1/weather-archive?latitude=52.50&longitude=13.40
