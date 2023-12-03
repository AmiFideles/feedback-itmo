const fetch = require('node-fetch');
const FormData = require('form-data');
const fs = require('fs');

const baseUrl = 'http://localhost/api/feedback/photo/';

const sendRequest = async (feedbackId, photoPath) => {
  const formData = new FormData();
  formData.append('file', fs.createReadStream(photoPath));

  try {
    const response = await fetch(baseUrl + feedbackId, {
      method: 'POST',
      body: formData,
    });

    if (response.ok) {
      console.log(`Request for feedback ID ${feedbackId} was successful.`);
    } else {
      const errorData = await response.json();
      console.error(`Request for feedback ID ${feedbackId} failed with status ${response.status}. Error:`, errorData);
    }
  } catch (error) {
    console.error(`Error for feedback ID ${feedbackId}:`, error);
  }
};

const generateAndSendPhotos = async () => {
  for (let i = 100; i <= 1000; i++) {
    const feedbackId = i;
    const photoPath = `./photo/photo_${i}.jpg`; // Замените на путь, где будут сохраняться фотографии
    const response = await fetch('https://source.unsplash.com/random');
    const buffer = await response.buffer();
    fs.writeFileSync(photoPath, buffer);

    await sendRequest(feedbackId, photoPath);
  }
};

generateAndSendPhotos();
