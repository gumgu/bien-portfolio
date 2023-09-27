<template>
  <div class="modal-container">
    <dialog open>
      <slot></slot>
    </dialog>
  </div>
</template>

<script>
export default {
  name: 'ErrorAlert'
}
</script>

<style scoped>
.modal-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
}

dialog {
  margin: 0;
  position: relative;
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
  padding: 20px;
}
</style>
