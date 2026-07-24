document.addEventListener('DOMContentLoaded', () => {
  const messages = [...document.querySelectorAll('.error-message, .error, .message.success, .message')]
    .map(el => ({ text: el.textContent.trim(), error: el.classList.contains('error') || el.classList.contains('error-message') }))
    .filter(item => item.text);
  if (messages.length) {
    const stack = document.createElement('div'); stack.className = 'toast-stack'; document.body.append(stack);
    messages.forEach(({text, error}) => {
      const toast = document.createElement('div'); toast.className = `toast ${error ? 'error' : 'success'}`;
      toast.textContent = text; stack.append(toast); setTimeout(() => toast.remove(), 4500);
    });
  }
});
