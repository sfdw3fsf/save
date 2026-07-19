import { useBreakpoint } from '@/hooks/useBreakpoint';
import { ICONS } from '@/utils/theme';
import { useMemo } from 'react';

type FileIconSizing = 'sm' | 'md';

type FileIconVariant = string | 'jpg' | 'jpeg' | 'pdf' | 'png' | 'docx' | 'doc';

type FileIconProps = {
  variant?: FileIconVariant;
  sizing?: FileIconSizing;
};

const FileIcon = ({ variant, sizing }: FileIconProps): JSX.Element => {
  const { hasMinWidthMd: isWeb } = useBreakpoint('md');
  const size = useMemo<FileIconSizing>(() => sizing ?? (isWeb ? 'md' : 'sm'), [isWeb, sizing]);

  const src = useMemo<string>(() => {
    switch (size) {
      case 'md': {
        switch (variant) {
          case 'jpg':
          case 'jpeg':
          case 'png':
            return ICONS.FILE.IMG.MD;
          case 'pdf':
            return ICONS.FILE.PDF.MD;
          case 'docx':
          case 'doc':
            return ICONS.FILE.DOCX.MD;
          default:
            return ICONS.FILE.DEFAULT.MD;
        }
      }
      case 'sm':
      default: {
        switch (variant) {
          case 'jpg':
          case 'jpeg':
          case 'png':
            return ICONS.FILE.IMG.SM;
          case 'pdf':
            return ICONS.FILE.PDF.SM;
          case 'docx':
          case 'doc':
            return ICONS.FILE.DOCX.SM;
          default:
            return ICONS.FILE.DEFAULT.SM;
        }
      }
    }
  }, [size, variant]);

  return <img src={src} alt='file-icon' />;
};

export default FileIcon;
