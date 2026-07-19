import { i18nResources } from '@/i18n/i18n.resource';
import { CheckCircleOutlined, WarningOutlined } from '@ant-design/icons';
import { cloneElement, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { twMerge } from 'tailwind-merge';

type Combine<T> = T extends { [key in keyof T]: infer K } ? K : never;
type Path<T, K extends boolean = false> = Combine<PathMap<T, K>>;
type ObjPath<key extends keyof T, T, K extends boolean> = `${string & key}.${string & Path<T[key], K>}`;
type PathMap<T, K extends boolean> = {
  [key in keyof T]: T[key] extends object ? ObjPath<key, T, K> | (K extends true ? key : never) : key;
};

export type FormMessageTranslateKey = Path<(typeof i18nResources)['form']>;

type FormMessageProps = {
  className?: string;
  variant?: 'success' | 'error' | 'info';
  message?: string;
  translateKey?: FormMessageTranslateKey;
  truncate?: boolean;
};

const FormMessage = ({ variant = 'info', message, translateKey, truncate = false, className }: FormMessageProps): JSX.Element => {
  const { t: tForm } = useTranslation('form');
  const variantIcon = useMemo(() => {
    switch (variant) {
      case 'success':
        return <CheckCircleOutlined />;
      case 'error':
        return <WarningOutlined />;
      case 'info':
      default:
        return;
    }
  }, [variant]);

  const variantColor = useMemo(() => {
    switch (variant) {
      case 'success':
        return 'text-green-500';
      case 'error':
        return 'text-red-500';
      case 'info':
      default:
        return 'text-slate-500';
    }
  }, [variant]);

  if (!message && !translateKey) return <></>;

  return (
    <div className={twMerge('flex w-full items-baseline justify-start gap-1', variantColor, className)}>
      {!!variantIcon && (
        <div className='flex w-fit h-[18px] items-center justify-center'>
          <div className='w-4 h-4 shrink-0'>{cloneElement(variantIcon, { className: 'w-full h-full text-inherit' })}</div>
        </div>
      )}

      <div className={twMerge('w-full min-h-[18px] text-xs font-normal', truncate && 'truncate')}>
        {message || (translateKey && tForm(translateKey))}
      </div>
    </div>
  );
};

export default FormMessage;
