<?php

namespace App\Form;

use App\Entity\Reclamation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceList;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('type', ChoiceType::class,[
                'choices'=> array(
                    'Contenu'=>'Contenu',
                    'Service technique'=>'Service technique',

                )
            ])

            ->add('text', TextType::class,[
                            'label'=>'Description',
                'attr'=>[
        'placeholder'=>'Description'
    ]
            ])

            ->add('screenshot', FileType::class, [
                'label'=>'Image',
                'mapped'=> false

            ])

            ->add('object', TextType::class,[
                'label'=>'sujet',
                'attr'=>[
                    'placeholder'=>'sujet'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
