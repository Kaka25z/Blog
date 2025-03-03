import React, { useState, useRef } from 'react';
import { Modal, Button, message } from 'antd';
import Cropper from 'react-cropper';
import Compressor from 'compressorjs';
import 'cropperjs/dist/cropper.css';
import http from '../../apis/axios';
import { useDispatch, useSelector } from 'react-redux';
import { setUserDetails } from '../../store/components/userDetail';

const ImageCropper = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [imageSrc, setImageSrc] = useState('');
  const cropperRef = useRef(null);
  const inputRef = useRef(null);
  const [fileName, setFileName] = useState('');
  const dispatch = useDispatch();
  const userDetails = useSelector((state: any) => state.userDetail);

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFileName(file.name);
    const reader = new FileReader();
    reader.onload = () => {
      setImageSrc(reader.result);
      setModalVisible(true);
    };
    reader.readAsDataURL(file);
  };

  const handleCrop = () => {
    const imageElement = cropperRef?.current;
    const cropper = imageElement?.cropper;
    const croppedCanvas = cropper.getCroppedCanvas();

    croppedCanvas.toBlob((blob) => {
      new Compressor(blob, {
        quality: 0.8, // 压缩质量
        success(compressedBlob) {
          console.log(compressedBlob, fileName)
          uploadImage(compressedBlob);
        },
        error(err) {
          console.error(err.message);
        },
      });
    }, 'image/jpeg');
  };

  
  const handleCancel = () => {
    setModalVisible(false);
    setImageSrc(''); 
  };


  const uploadImage = async (blob) => {
    const reader = new FileReader();
    reader.onloadend = async function() {
      const base64data = reader.result;
      console.log(base64data);
      try {
        const token = sessionStorage.getItem('token');
        const now = new Date();
        const localTime = now.toLocaleString();
        const response = await http({
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          url: '/api/protected/update-useravatar',
          method: 'POST',
          data: {
            id: userDetails.id,
            image: base64data,
            updateTime: localTime,
          }
        })
        if (response.data.code === 200) {
          const data = await response.data.data
          const newUserDetails = {
            ...userDetails,
            userAvatar: data,
          }
          dispatch(setUserDetails(newUserDetails));
          message.success(response.data.message);
          setImageSrc('');
        } else {
          message.error(response.data.message)
        }
      } catch (error) {
        console.error('上传错误', error);
      }
    }
    reader.readAsDataURL(blob);
  };

  return (
    <div>
      <Button type="primary" onClick={() => inputRef.current.click()}>
        选择图片
      </Button>
      <input
        type="file"
        accept="image/*"
        style={{ display: 'none' }}
        ref={inputRef}
        onChange={handleFileChange}
      />
      <Modal
        title="裁剪图片"
        open={modalVisible}
        onOk={() => {
          handleCrop();
          setModalVisible(false);
        }}
        onCancel={handleCancel}
      >
        <Cropper
          src={imageSrc}
          style={{ height: '100%', width: '100%' }}
          initialAspectRatio={1}
          guides={false}
          ref={cropperRef}
          dragMode='move'
        />
      </Modal>
    </div>
  );
};

export default ImageCropper;
