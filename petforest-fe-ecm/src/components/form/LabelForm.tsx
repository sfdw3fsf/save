import clsx from 'clsx';
import React from 'react';

export type LabelFormProps = {
  label?: React.ReactNode;
  extraLabel?: React.ReactNode;
  isRequired?: boolean;
  labelWidth?: number;
  bordered?: boolean;
  children?: React.ReactNode;
  isLabelForm?: boolean;
  className?: string;
  classNameLabel?: string;
  position?: 'top' | 'left'; // Added position options
};

const LabelForm = ({
  label,
  isRequired,
  bordered,
  extraLabel,
  labelWidth = 150,
  children,
  isLabelForm = true,
  className,
  classNameLabel,
  position = 'top', // Default to 'top'
}: LabelFormProps) => {
  return isLabelForm ? (
    <div className={clsx('flex', !bordered && '!m-0', className, position === 'top' ? 'flex-col' : 'items-start')}>
      {label && (
        <div className='flex items-center gap-[3px]' style={{ width: labelWidth }}>
          <div className='flex items-start gap-[2px]'>
            <div className={clsx(`pack-text-gray-800-14-m whitespace-nowrap ${classNameLabel}`)}>
              {label}
              {isRequired && <span className='text-red-500'>*</span>}
              {extraLabel}
            </div>
          </div>
        </div>
      )}
      <div className='flex-auto '>{children}</div> {/* Added margin-top for spacing */}
    </div>
  ) : (
    <div className={className}>{children}</div>
  );
};

export default LabelForm;
