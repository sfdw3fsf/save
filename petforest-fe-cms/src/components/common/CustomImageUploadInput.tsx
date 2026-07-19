import { PlusOutlined } from '@ant-design/icons';
import { Image, Upload, UploadProps } from 'antd';
import { UploadFile } from 'antd/es/upload';
import { GetProp } from 'antd/lib';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0];

const getBase64 = (file: FileType): Promise<string> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = (error) => reject(error);
  });

type CustomFileUploadProps = {
  value?: UploadFile<any>[];
  onChange?: (files: UploadFile<any>[]) => void;
  multiple?: boolean;
  fileType?: 'image';
  displayName?: boolean;
} & Omit<UploadProps, 'fileName' | 'onChange'>;

const CustomImageUploadInput = ({
  value,
  multiple = false,
  disabled,
  onChange,
  fileType,
  maxCount,
  displayName = true,
}: CustomFileUploadProps) => {
  const { t: tCommon } = useTranslation('common');
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [fileList, setFileList] = useState<UploadFile[]>([]);

  useEffect(() => {
    if (!value) return;
    setFileList(value);
  }, [value]);

  const handlePreview = async (file: UploadFile) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj as FileType);
    }

    setPreviewImage(file.url || (file.preview as string));
    setPreviewOpen(true);
  };

  const handleChange: UploadProps['onChange'] = async ({ fileList: newFileList }) => {
    const updatedFileList = await Promise.all(
      newFileList.map(async (file) => {
        if (!file.url && !file.preview && file.originFileObj) {
          file.preview = await getBase64(file.originFileObj);
        }
        return file;
      }),
    );
    setFileList(updatedFileList);
    onChange?.(updatedFileList);
  };

  const uploadButton = (
    <button style={{ border: 0, background: 'none' }} type='button'>
      <PlusOutlined />
      <div style={{ marginTop: 8 }}>{tCommon('button.upload')}</div>
    </button>
  );

  return (
    <>
      {displayName && (
        <div style={{ marginBottom: '16px' }}>
          {fileList.length > 0 && <div className='flex'>{fileList.map((item) => item.name).join(', ')}</div>}
        </div>
      )}
      <Upload
        listType='picture-card'
        fileList={fileList}
        onPreview={handlePreview}
        onChange={handleChange}
        beforeUpload={() => {
          return false;
        }}
        disabled={disabled}
        multiple={multiple}
        maxCount={maxCount}
        accept={fileType === 'image' ? 'image/*' : undefined}>
        {fileList.length >= (maxCount || 15) ? null : uploadButton}
      </Upload>
      {previewImage && (
        <Image
          wrapperStyle={{ display: 'none' }}
          preview={{
            visible: previewOpen,
            onVisibleChange: (visible) => setPreviewOpen(visible),
            afterOpenChange: (visible) => !visible && setPreviewImage(''),
          }}
          src={previewImage}
        />
      )}
    </>
  );
};

export default CustomImageUploadInput;
