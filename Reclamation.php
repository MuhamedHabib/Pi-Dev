<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Reclamation
 *
 * @ORM\Table(name="reclamation")
 * @ORM\Entity(repositoryClass=ReclamationRepository::class)
 */
class Reclamation
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_reclamation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idReclamation;

    /**
     * @var string|null
     *
     * @ORM\Column(name="type", type="string", length=99, nullable=true)
     */
    private $type;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="date_creation", type="date", nullable=true)
     */
    private $dateCreation;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="date_validation", type="date", nullable=true)
     */
    private $dateValidation;

    /**
     * @var int|null
     *
     * @ORM\Column(name="id_user", type="integer", nullable=true)
     */
    private $idUser;

    /**
     * @var string
     *
     * @ORM\Column(name="text", type="string", length=99, nullable=false)
     * @Assert\NotBlank(message="Veuillez remplir ce champs")
     * @Assert\Length(
     *     min="10",
     *     max="100",
     *     minMessage="Doit contenir {{ limit }} caractéres",
     *     maxMessage="Ne doit pas dépasser {{ limit }} caractéres",
     *     )
     */
    private $text;

    /**
     * @var string|null
     *
     * @ORM\Column(name="statut", type="string", length=99, nullable=true)
     */
    private $statut;

    /**
     * @var string|null
     *
     * @ORM\Column(name="screenshot", type="string", length=99, nullable=true)
     */
    private $screenshot;

    /**
     * @var string
     * @Assert\NotBlank(message="Veuillez remplir ce champs")
     * @Assert\Length(
     *     max="30",
     *     maxMessage="Ne doit pas dépasser {{ limit }} caractéres",
     *     )
     */
    private $object;

    public function getIdReclamation(): ?int
    {
        return $this->idReclamation;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(?string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->dateCreation;
    }

    public function setDateCreation(?\DateTimeInterface $dateCreation): self
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    public function getDateValidation(): ?\DateTimeInterface
    {
        return $this->dateValidation;
    }

    public function setDateValidation(?\DateTimeInterface $dateValidation): self
    {
        $this->dateValidation = $dateValidation;

        return $this;
    }

    public function getIdUser(): ?int
    {
        return $this->idUser;
    }

    public function setIdUser(?int $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }

    public function getText(): ?string
    {
        return $this->text;
    }

    public function setText(string $text): self
    {
        $this->text = $text;

        return $this;
    }

    public function getStatut(): ?string
    {
        return $this->statut;
    }

    public function setStatut(?string $statut): self
    {
        $this->statut = $statut;

        return $this;
    }

    public function getScreenshot(): ?string
    {
        return $this->screenshot;
    }

    public function setScreenshot(?string $screenshot): self
    {
        $this->screenshot = $screenshot;

        return $this;
    }

    public function getObject(): ?string
    {
        return $this->object;
    }

    public function setObject(string $object): self
    {
        $this->object = $object;

        return $this;
    }


}
