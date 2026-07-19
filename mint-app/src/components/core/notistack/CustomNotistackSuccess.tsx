import CheckCircleIcon from '@/components/icons/CheckCircleIcon';
import { SnackbarContent, closeSnackbar } from 'notistack';
import React from 'react';
import { useTranslation } from 'react-i18next';
import Alert from '../alert/Alert';
import { CustomNotiStackProps } from './notistack.type';

interface CustomNotistackSuccessProps extends CustomNotiStackProps {}

const CustomNotistackSuccess = React.forwardRef<HTMLDivElement, CustomNotistackSuccessProps>((props, ref) => {
  const { t: tCommon } = useTranslation('common');

  const { id, message, allowDownload, anchorOrigin, hideIconVariant, persist, autoHideDuration, action, ...others } = props;

  return (
    <SnackbarContent ref={ref} {...others}>
      <Alert variant='toast-success' onClose={() => closeSnackbar(id)} icon={<CheckCircleIcon />}>
        {message ? message : tCommon('default_message.success')}
      </Alert>
    </SnackbarContent>
  );
});

CustomNotistackSuccess.displayName = 'CustomNotistackSuccess';

const CustomNotistackSuccessMemo = React.memo(CustomNotistackSuccess);

export default CustomNotistackSuccessMemo;
