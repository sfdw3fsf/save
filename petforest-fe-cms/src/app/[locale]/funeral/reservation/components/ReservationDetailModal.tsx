'use client';
import { Divider } from 'antd';
import { FRD_AdditonalCost } from './FRD_AdditonalCost';
import { FRD_AnnounceCoupon } from './FRD_AnnounceCoupon';
import { FRD_Funeral } from './FRD_Funeral';
import { FRD_Guardian } from './FRD_Guardian';
import { FRD_HeadingForm } from './FRD_HeadingForm';
import { FRD_Hospital } from './FRD_Hospital';
import { FRD_PartnerCompany } from './FRD_PartnerCompany';
import { FRD_PaymentMethod } from './FRD_PaymentMethod';
import { FRD_Pet } from './FRD_Pet';
import { FRD_Service } from './FRD_Service';
import FRD_SettlementDetail from './FRD_SettlementDetail';

const ReservationDetailModal = () => {
  return (
    <>
      <div className='mb-2 overflow-hidden'>
        <div className='grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-4 pt-6 lg:pr-4'>
          <FRD_HeadingForm />
        </div>
        <div className='grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-6 lg:gap-y-4 pt-8 lg:pt-6 lg:pr-4'>
          <FRD_Pet />
          <FRD_Guardian />
        </div>
        <Divider className='border-[5px] my-8 lg:my-6' />
        <div className='grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-4 lg:pr-4'>
          <FRD_Funeral />
          <FRD_Service />
        </div>
        <Divider className='border-[5px]' />
        <div className='grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-4 lg:pr-4'>
          <FRD_AnnounceCoupon />
        </div>
        <Divider className='border-[5px]' />
        <div className='grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-4 lg:pr-4'>
          <FRD_Hospital />
          <FRD_PartnerCompany />
        </div>
        <Divider className='border-[5px]' />
        <div className='grid grid-cols-1 md:grid-cols-2 gap-x-12 gap-y-4 lg:pr-4'>
          <FRD_AdditonalCost />
          <FRD_PaymentMethod />
        </div>

        <FRD_SettlementDetail />
      </div>
    </>
  );
};

export default ReservationDetailModal;
