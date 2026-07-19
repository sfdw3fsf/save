import CenteredModal from '@/components/common/CenteredModal';
import CustomButton from '@/components/common/CustomButton';
import { KAKAO_MESSAGE_TYPE } from '@/utils/enums/common.enums';
import { Divider } from 'antd';
import React from 'react';
import { useTranslation } from 'react-i18next';

type OssuarySelectNotificationModalProps = {
  isOpen: boolean;
  onClose: () => void;
  title?: string;
  onCreateMessage: (type: KAKAO_MESSAGE_TYPE) => void;
};

const OssuarySelectNotificationModal = ({ isOpen, onClose, onCreateMessage }: OssuarySelectNotificationModalProps) => {
  const { t: tOssuary } = useTranslation('ossuary');
  const { t: tEnum } = useTranslation('enum');

  return (
    <CenteredModal isFooter={false} isOpen={isOpen} onCancel={onClose} title={tOssuary('notification_talk')}>
      <Divider className='!m-0' />
      <div className='flex flex-col'>
        {Object.values(KAKAO_MESSAGE_TYPE).map((type, index) => (
          <React.Fragment key={index}>
            {index > 0 && <Divider className='!m-0' />}
            <CustomButton className='!h-[52px]' type='text' onClick={() => onCreateMessage(type)}>
              {tEnum(`kakao_message_type.${type}`)}
            </CustomButton>
          </React.Fragment>
        ))}
      </div>
    </CenteredModal>
  );
};

export default OssuarySelectNotificationModal;
