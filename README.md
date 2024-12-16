# Pothole Detection and Reporting System

### ML Model Link
[GitHub Repository](https://github.com/akhulisumit/Pothole-detection-ML-Model)

### UI Link
[Canva Design](https://www.canva.com/design/DAGX-yV_znw/WCRpqGLzFSkLEU5M7iUMEg/edit?utm_content=DAGX-yV_znw&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

## Overview
This project is a mobile application designed to detect potholes using a trained machine learning model. Users can:
- Capture images of suspected potholes.
- Verify the presence of a pothole.
- Tag the location.
- Report it directly on Twitter to raise public awareness and prompt quicker responses from authorities.

## Features

- **Image Capture**: Take a photo of the suspected pothole directly within the app.

- **Pothole Detection**: Use a machine learning model to analyze the image and determine if it contains a pothole.

- **Location Detection**: Automatically detect the user's current location using an API.

- **Twitter Integration**: Report the detected pothole by uploading the image and tagging the location on Twitter.

## How It Works

### 1. Capture Image
- Open the app and capture an image of a pothole.

### 2. Pothole Detection
- The app uploads the image to the backend.
- The backend runs the trained model to determine if the image contains a pothole.

### 3. Location Detection
- The app fetches the user's current location using the Geolocation API.
- Displays the location on a map for confirmation.

### 4. Twitter Reporting
- Upon detection, the app prepares a tweet with the image and the location.
- Users can post the tweet directly from the app.
