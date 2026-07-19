import { SnackbarContent, closeSnackbar } from 'notistack';
import React from 'react';
import { useTranslation } from 'react-i18next';
import Alert from '../alert/Alert';
import { CustomNotiStackProps } from './notistack.type';

interface CustomNotistackFailProps extends CustomNotiStackProps {}

const CustomNotistackFail = React.forwardRef<HTMLDivElement, CustomNotistackFailProps>((props, ref) => {
  const { t: tCommon } = useTranslation('common');

  const { id, message, allowDownload, anchorOrigin, iconVariant, hideIconVariant, persist, autoHideDuration, action, ...others } =
    props;

  return (
    <SnackbarContent ref={ref} role='alert' {...others}>
      <Alert variant='toast-error' onClose={() => closeSnackbar(id)}>
        {message ? message : tCommon('default_message.error')}
      </Alert>
    </SnackbarContent>
  );
});

CustomNotistackFail.displayName = 'CustomNotistackFail';

const CustomNotistackFailMemo = React.memo(CustomNotistackFail);

export default CustomNotistackFailMemo;
