<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <input v-model="feedbackId" type="number" placeholder="Enter feedback ID" />
    <input v-model="imagePath" type="text" placeholder="Enter image path" />
    <button @click="sendRequest">Send</button>
    <button @click="insertImagePath">Insert Image Path</button>
    <div v-if="response">
      <h3>Response:</h3>
      <p>{{ response }}</p>
    </div>
    <img :src="imagePath" alt="Inserted Image" v-if="imagePath" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const feedbackId = ref('');
const imagePath = ref('');
const response = ref('');

const sendRequest = () => {
  fetch(`/api/feedback/${feedbackId.value}`)
    .then(response => response.json())
    .then(data => {
      response.value = data;
    })
    .catch(error => {
      console.error(error);
    });
};

const insertImagePath = () => {
  const imgElement = document.querySelector('img');
  if (imgElement) {
    imgElement.src = imagePath.value;
  }
};
</script>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}
h3 {
  font-size: 1.2rem;
}
.greetings h1,
.greetings h3 {
  text-align: center;
}
@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>