import { createToast } from "mosha-vue-toastify";
import "mosha-vue-toastify/dist/style.css";

const notifications = () => {
  const notifySuccess = (title) => {
    createToast(title,
      {
        showIcon: 'true',
        position: 'bottom-right',
        type: 'success',
        transition: 'bounce',
        hideProgressBar: 'true',
        toastBackgroundColor: "#334666",
      })
    };
  const notifySuccessDescription = (title, description) => {
    createToast({
      title,
      description,
    },
    {
      showIcon: 'true',
      position: 'bottom-right',
      type: 'success',
      transition: 'bounce',
      hideProgressBar: 'true',
      toastBackgroundColor: "#334666",
    })
  };
  const notifyDanger = (title) => {
    createToast(title,
      {
        showIcon: 'true',
        position: 'bottom-right',
        type: 'danger',
        transition: 'bounce',
        hideProgressBar: 'true',
        toastBackgroundColor: "#F5CEC7",
      })
    };
    const notifyDangerDescription = (title, description) => {
      createToast({
        title,
        description,
      },
      {
        showIcon: 'true',
        position: 'bottom-right',
        type: 'danger',
        transition: 'bounce',
        hideProgressBar: 'true',
        toastBackgroundColor: "#F5CEC7",
      })
  };

  return {
    notifySuccess,
    notifySuccessDescription,
    notifyDanger,
    notifyDangerDescription,
  };
};

export default notifications;

