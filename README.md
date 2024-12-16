Pothole Detection and Reporting System

Overview

This project is a mobile application designed to detect potholes using a trained machine learning model. Users can capture images of suspected potholes, verify the presence of a pothole, tag the location, and report it directly on Twitter to raise public awareness and prompt quicker responses from authorities.

Features

Image Capture: Take a photo of the suspected pothole directly within the app.

Pothole Detection: Use a machine learning model to analyze the image and determine if it contains a pothole.

Location Detection: Automatically detect the user's current location using an API.

Twitter Integration: Report the detected pothole by uploading the image and tagging the location on Twitter.

How It Works

Capture Image: Open the app and capture an image of a pothole.

Pothole Detection:

The app uploads the image to the backend.

The backend runs the trained model to determine if the image contains a pothole.

Location Detection:

The app fetches the user's current location using the Geolocation API.

Displays the location on a map for confirmation.

Twitter Reporting:

Upon detection, the app prepares a tweet with the image and the location.

Users can post the tweet directly from the app.